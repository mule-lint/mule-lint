# Mule Lint Project

We believe building some types of software are not trivial and when there are
many applications, it is a good thing to make sure you have some consistency
around certain pieces of software. Primarily that logging follows a very strict
convention for your organization or project. So having some analysis across all
your systems would be a good thing. That is how this started.

We need a way to check on what log categories we have, and how well our
structured (or lack of following the structured format) was occuring across our
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

## Initial Rule Format
There are quite a few things we want to do, but to get out a [MVP](https://en.wikipedia.org/wiki/Minimum_viable_product)
we are trying to find something that is easier for us to deliver, then to improve upon later.

This is what we are working towards for a `rules.groovy` example:

```
version '0.0.1'

element 'logger' hasAttribute 'category'

element 'logger' hasParent 'until-successful'

element 'http:request' hasChild 'User-Agent'

element 'http:request' hasChild 'http:header' withAttribute 'headerName' havingValue 'User-Agent' withAttribute 'value' havingValue 'my-bot-name 1.0'
```

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Maven
If you are just wanting to use this with a Maven build, then please refer to
[mule-lint-maven-plugin project](https://github.com/Nuisto/mule-lint-maven-plugin) which
provides a much easier way of invocoation rather than calling this project
directly.

### Prerequisites

This project is build with Gradle 4.1-rc-1, Maven 3.5.0, and Java 1.8.0_121.

```
$ git clone git@github.com:Nuisto/mule-lint.git
$ cd mule-lint
$ gradle build
```

## Running the tests

All test are ran using Gradle. There are no category of tests (i.e. unit tests or integration tests), everything is packaged together under `src/test/groovy`

```
$ gradle build
```


## Built With

* [Gradle](https://gradle.org/) - Build system
* [Groovy](http://groovy-lang.org/) - Written in

## Contributing

Please read [CONTRIBUTING.md](https://github.com/nuisto/mule-lint/CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/nuisto/mule-lint/tags). 

## Authors

* **Chad Gorshing** - *Initial work* - [cgorshing](https://gens.io/profile/cgorshing)

See also the list of [contributors](https://github.com/nuisto/mule-lint/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

* Hat tip to [PurpleBooth](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2) as she provided the starting point of this README and other project related files.

[![Analytics](https://beacon-cgorshing.appspot.com/UA-24556575-4/nuisto/mule-lint/README.md?pixel)](https://github.com/nuisto/mule-lint/README.md)
