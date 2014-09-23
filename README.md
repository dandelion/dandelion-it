Dandelion Integration Tests
========================

This repository contains a set of integration tests for all Dandelion components, including the core of the framework.

All tests use the following technology stack:

 * [FluentLenium](https://github.com/FluentLenium/FluentLenium), which provides a fluent interface to the [Selenium Web Driver](http://seleniumhq.org/docs/03_webdriver.html) and pulls [Selenium](http://docs.seleniumhq.org/) 
 * [GhostDriver](https://github.com/detro/ghostdriver) which provides [Web Driver](http://seleniumhq.org/docs/03_webdriver.html) bindings for Java and uses [PhantomJS](http://phantomjs.org/) as back-end
 * [Phanbedder](https://github.com/anthavio/phanbedder) which smartly ships [PhantomJS](http://phantomjs.org/) binaries, thus simplifying the required configuration both on workstations and in the Cloud 
 * [AssertJ](http://joel-costigliola.github.io/assertj/) as a replacement for [JUnit](http://junit.org/) assertions 
 * [Jetty](http://www.eclipse.org/jetty/) as an embedded Web server

And depending on the test context:

 * [Thymeleaf](http://www.thymeleaf.org/)
 * [JSP](http://www.oracle.com/technetwork/java/javaee/jsp/index.html)
 * [Spring](http://projects.spring.io/spring-framework/) / [Jackson](http://jackson.codehaus.org/)

## IDE configuration

### Using Eclipse

Just right-click on the dandelion-it Maven project, then select Maven in the left menu and enter the following pattern:

````
[component-name]-[template-engine]
````

Where `component-name` is one of:

 * `core` for [Dandelion-Core](http://dandelion.github.io/dandelion/)
 * `datatables` for the [Dandelion-Datatables](http://dandelion.github.io/datatables/) component
 * `select2` soon...

And `template-engine` is one of:

 * `jsp` for executing the integration tests using JSP
 * `tml` for executing the integration tests using Thymeleaf

For example, when activating the following profile, IT related to Dandelion-Datatables only will be executed.

<img src="https://cloud.githubusercontent.com/assets/1398586/4368757/733192f8-42f2-11e4-981a-58272683e83b.png" style="text-align:center;" />

### Using IntelliJ

TODO

## Firing tests

In order to launch tests from Maven, execute:

````
mvn verify -P [component-name]-[template-engine]
````

See above for the signification of `component-name` and `template-engine`.

## Continuous testing

All integration tests are automatically executed as soon as the corresponding component is built on [Jenkins](https://dandelion.ci.cloudbees.com). All IT statuses can be seen below:

<table>
<tr>
<td><a href="http://dandelion.github.io/dandelion/">Dandelion-Core</a></td>
<td><a href='https://dandelion.ci.cloudbees.com/job/dandelion-core-it/'><img src='https://dandelion.ci.cloudbees.com/job/dandelion-core-it/badge/icon'></a></td>
</tr>
<tr>
<td><a href="http://dandelion.github.io/datatables/">Dandelion-Datatables</a></td>
<td><a href='https://dandelion.ci.cloudbees.com/job/dandelion-datatables-it/'><img src='https://dandelion.ci.cloudbees.com/job/dandelion-datatables-it/badge/icon'></a></td>
</tr>
<tr>
<td>Dandelion-Select2</td>
<td>Soon...</td>
</tr>
</table>

=
The [Dandelion team](http://dandelion.github.io/team/).
