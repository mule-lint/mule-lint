# Mule Statis Analysis

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
  `transactionid`?

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
Give examples
```

### Installing

A step by step series of examples that tell you have to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Gradle](https://gradle.org/) - Build system
* [Groovy](http://groovy-lang.org/) - Written in

## Contributing

Please read [CONTRIBUTING.md](https://github.com/nuisto/mule-static-analysis) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/nuisto/mule-static-analysisyour/tags). 

## Authors

* **Chad Gorshing** - *Initial work* - [cgorshing](https://gens.io/profile/cgorshing)

See also the list of [contributors](https://github.com/nuisto/mule-static-analysis/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to https://gist.github.com/PurpleBooth/109311bb0361f32d87a2 as this
  was the starting point of this README and other project related files

[![Analytics](https://beacon-cgorshing.appspot.com/UA-58872618-3/nuisto/mule-static-analysis/README.md?pixel)](https://github.com/nuisto/mule-static-analysis)
