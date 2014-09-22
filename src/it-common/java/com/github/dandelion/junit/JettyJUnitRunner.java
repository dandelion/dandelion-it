/*
 * [The "BSD licence"]
 * Copyright (c) 2013-2014 Dandelion
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of Dandelion nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.dandelion.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import com.github.dandelion.AbstractIT;
import com.github.dandelion.TemplateEngine;
import com.github.dandelion.ThymeleafServlet;
import com.github.dandelion.mock.Mock;
import com.github.dandelion.mock.Person;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

/**
 * <p>
 * Custom JUnit runner that starts an embedded Jetty server before all tests and
 * stops it after all tests.
 * </p>
 * <p>
 * The server is configured using the {@code @ServerConfig} annotation.
 * </p>
 * 
 * <p>
 * If some system properties are needed inside tests, they must be set in a
 * static method annotated with {@code @BeforeClass}.
 * </p>
 * 
 * <pre>
 * {@code @BeforeClass}
 * public static void setup() {
 *    System.setProperty("some-property", "some-value");
 * }
 * </pre>
 * <p>
 * Don't forget to clear the property after the execution of all tests in a
 * static method annotated with {@code @AfterClass}.
 * </p>
 * 
 * <pre>
 * {@code @AfterClass}
 * public static void teardown() {
 *    System.clearProperty("some-property");
 * }
 * </pre>
 * 
 * @author Thibault Duchateau
 * @since 0.11.0
 */
public class JettyJUnitRunner extends BlockJUnit4ClassRunner {

	private Server httpServer;

	public JettyJUnitRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	@Override
	protected Statement withBeforeClasses(Statement statement) {
		List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(BeforeClass.class);
		Statement customStatement = new StartServerStatement(statement);
		return befores.isEmpty() ? customStatement : new RunBefores(customStatement, befores, null);
	}

	@Override
	protected Statement withAfterClasses(Statement statement) {
		List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(AfterClass.class);
		Statement customStatement = new StopServerStatement(statement);
		return afters.isEmpty() ? customStatement : new RunAfters(customStatement, afters, null);
	}

	private static Server createHttpServer(ServerConfig serverConfig) {

		final Server server = new Server(serverConfig.port());

		final WebAppContext context = new WebAppContext();
		context.setServer(server);
		context.setContextPath(serverConfig.contextPath());
		context.setWar(serverConfig.webappBase());
		server.setHandler(context);

		switch (serverConfig.templateEngine()) {
		case JSP:
			// Add support for JSP
			ServletHolder jsp = context.addServlet(JspServlet.class, "*.jsp");
			jsp.setInitParameter("classpath", context.getClassPath());
			break;
		case THYMELEAF:
			// Add support for Thymeleaf
			context.addServlet(ThymeleafServlet.class, "/thymeleaf/*");
			break;
		default:
			throw new IllegalArgumentException();
		}

		context.setAttribute("persons", Mock.persons);
		context.setAttribute("emptyList", new ArrayList<Person>());
		context.setAttribute("nullList", null);

		return server;
	}

	private void startHttpServer() throws InitializationError {

		ServerConfig serverConfig = findAnnotation(getTestClass().getJavaClass(), ServerConfig.class);
		if (serverConfig == null) {
			throw new IllegalArgumentException("The ServerConfig annotation is required");
		}

		try {
			this.httpServer = createHttpServer(serverConfig);
			startServer(this.httpServer);
		}
		catch (Exception e) {
			throw new InitializationError(e);
		}
	}

	private void stopHttpServer() throws InitializationError {
		try {
			stopServer(this.httpServer);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new InitializationError(e);
		}
	}

	private static void startServer(Server server) throws Exception {

		try {
			System.out.println("Starting embedded Jetty server");
			server.start();
			// server.join();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new InitializationError(e);
		}
	}

	private static void stopServer(Server server) throws Exception {

		try {
			server.stop();
			// server.join();
		}
		catch (Exception e) {
			throw new InitializationError(e);
		}
	}

	protected class StartServerStatement extends Statement {

		private Statement nextStatement;

		public StartServerStatement(Statement nextStatement) {
			this.nextStatement = nextStatement;
		}

		@Override
		public void evaluate() throws Throwable {
			startHttpServer();
			nextStatement.evaluate();
		}
	}

	protected class StopServerStatement extends Statement {

		private Statement previousStatement;

		public StopServerStatement(Statement previousStatement) {
			this.previousStatement = previousStatement;
		}

		@Override
		public void evaluate() throws Throwable {

			List<Throwable> errors = new ArrayList<Throwable>();
			try {
				this.previousStatement.evaluate();
			}
			catch (Throwable e) {
				errors.add(e);
			}

			try {
				stopHttpServer();
			}
			catch (Exception e) {
				errors.add(e);
			}

			if (errors.isEmpty()) {
				return;
			}
			if (errors.size() == 1) {
				throw errors.get(0);
			}
			throw new MultipleFailureException(errors);
		}
	}

	/**
	 * TODO
	 * 
	 * @author Thibault Duchateau
	 * @since 0.11.0
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface ServerConfig {

		public String host() default AbstractIT.SERVER_HOST;

		public int port() default AbstractIT.SERVER_PORT;

		public String contextPath() default "/";

		public String webappBase() default "src/main/webapp";

		public TemplateEngine templateEngine();
	}
}
