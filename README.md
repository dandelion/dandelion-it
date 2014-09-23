Dandelion Integration Tests
========================

TODO

## IDE configuration

### Using Eclipse

Just right-click on the dandelion-it Maven project, then Maven and enter the following active profile:

````
[component-name]-[template-engine]
````

Where `component-name` is one of:

 * `core` for the Dandelion-Core component
 * `datatables`
 * `select2`

And `template-engine` is one of:

 * `jsp` for executing the integration tests using JSP
 * `tml` for executing the integration tests using Thymeleaf

TODO: screenshot

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

[![Core IT Status](https://dandelion.ci.cloudbees.com/job/dandelion-core-it/badge/icon)](https://dandelion.ci.cloudbees.com/job/dandelion-core-it/)

[![Datatables IT Status](https://dandelion.ci.cloudbees.com/job/dandelion-datatables-it/badge/icon)](https://dandelion.ci.cloudbees.com/job/dandelion-datatables-it/)

=
The [Dandelion team](http://dandelion.github.io/team/).
