# Mule Lint Project

We believe building some types of software are not trivial and when there are
many applications, it is a good thing to make sure you have some consistency
around certain pieces of software. Primarily that logging follows a very strict
convention for your organization or project. So having some analysis across all
your systems would be a good thing. That is how this started.

We need a way to check on what log categories we have, and how well our
structured (or lack of following the structured format) was occurring across our
entire code base.

I would like different information about Mule code.
What http:request does not have a Content-Type or User-Agent defined?
What logger’s do not have a category or “transactionId” in the message?
What flows are sync or async?
I’m thinking more information about the code base. A static analysis.

For the initial releases of this project, this will be information giving, not enforcing.
A report will be provided with the findings that can be used for historical keeping, but could
also be used by other pieces in your pipeline to assert a value/count does not exceed a
threshold you define.

Such as find regex within loggers message attribute (or category attribute)

Or does does attribute X have values within this acceptable list?

We also come across where certain elements needed to configured a certain way,
and often devs, by mistake or otherwise, would change these attributes. So this
is intended to provide that safety net, that what we end up deploying out
satisfies a certain criteria.

The main purpose goal of this project is to detect code differences that will
impact functional operations. An initial list of items we would like to check
are:
* Does every http:request have a User-Agent defined?
* Does every http:request have a (context specific) `X-\*` header defined?
* Does every logger have a category that is known?
* (Longer term goal) What logger does not follow our team defined structured
  logging format?
* Do we have any logger that references `#[payload]`
* Do we have any logger that does not reference (context specific)
  `transactionId`?

## Summary

  - [Initial Rule Format](#initial-rule-format)
  - [Getting Started](#getting-started)
  - [Running the tests](#running-the-tests)
  - [Built With](#built-with)
  - [Contributing](#contributing)
  - [Versioning](#versioning)
  - [Authors](#authors)
  - [License](#license)
  - [Acknowledgments](#acknowledgments)

## Initial Rule Format
There are quite a few things we want to do, but to get out a [MVP](https://en.wikipedia.org/wiki/Minimum_viable_product)
we are trying to find something that is easier for us to deliver, then to improve upon later.

This is what we are working towards for a `rules.groovy` example:

```
version '0.0.1'

flow are camel cased

element 'logger' hasAttribute 'category'

element 'logger' hasParent 'until-successful'

element 'http:request' hasChild 'User-Agent'

element 'http:request' hasChild 'http:header' withAttribute 'headerName' havingValue 'User-Agent' withAttribute 'value' havingValue 'my-bot-name 1.0'
```

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Maven
If you are just wanting to use this with a Maven build, then please refer to
[mule-lint-maven-plugin project](https://github.com/mule-lint/mule-lint-maven-plugin) which
provides a much easier way of invocation rather than calling this project
directly.

### Example Project
We have put together a trivial Mule app that we will keep
up-to-date to reflect new features of mule-lint. You can find it [on Github](https://github.com/mule-lint/example-mule-lint-project).

### Prerequisites

Java8 is required, there are [known issues](https://github.com/mule-lint/mule-lint/issues/15) with anything greater than Java8.

Versions up to 0.4.0 were built with Gradle 4.1-rc-1 (the repo includes its own
`./gradlew`).

Versions 0.5.0 and above were built with Gradle 5.

```
$ git clone git@github.com:mule-lint/mule-lint.git
$ cd mule-lint
$ ./gradlew build
```

## Running the tests

All tests are ran using Gradle. There are no category of tests (i.e. unit tests or integration tests), everything is packaged together under `src/test/groovy`.
Starting with version 0.5.0 we use JUnit5 (Jupiter tests)

```
$ ./gradlew build
```


## Built With

* [Java](https://java.sun.com/) - Java8 is required, there are [known issues](https://github.com/mule-lint/mule-lint/issues/15) with anything
  greater than Java8.
* [Gradle](https://gradle.org/) - Build system, please use the wrapper within
  the repo `./gradlew`
* [Groovy](http://groovy-lang.org/) - Written in Groovy using the version that Mule depends upon as well. Currently v2.4.4

The following is what MuleSoft recommends for the different versions of Mule.
```
4.1.4 JDK 1.8.0 (Recommended JDK 1.8.0_171/72)
4.1.0 JDK 1.8.0 (Recommended JDK 1.8.0_151/52)
3.9.2 Oracle JDK 1.8.0 (Recommended JDK 1.8.0_191/192), IBM JDK 1.8, OpenJDK 8
3.9.1 Oracle JDK 1.8.0 (Recommended JDK 1.8.0_151/52), IBM JDK 1.8, OpenJDK 8
3.9.0 JDK 1.8.0 (Recommended JDK 1.8.0_144)
```


## Contributing

Please read [CONTRIBUTING.md](https://github.com/mule-lint/mule-lint/CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

If you are interested in the DSL used then give the docs a read, they are a [great starting point](http://docs.groovy-lang.org/latest/html/documentation/core-domain-specific-languages.html).

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/mule-lint/mule-lint/tags). 

## Authors

* **Chad Gorshing** - *Initial work* - [cgorshing](https://gens.io/profile/cgorshing)

See also the list of [contributors](https://github.com/mule-lint/mule-lint/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

* Hat tip to [PurpleBooth](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2) as she provided the starting point of this README and other project related files.
