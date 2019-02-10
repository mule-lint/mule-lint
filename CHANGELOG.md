# Changelog
All notable changes to this project will be documented in this file.

## [Unreleased]
### Added
### Changed
- 
### Deprecated
### Removed
### Fixed
### Security

## [0.5.0] - 2019-02-10
### Added
- Camel casing checks on flow and sub-flow elements.
- Command line parameter added to specify a dictionary of words to check against for camel casing.
- Now when a word is used that isn't a part of the DSL, you will get a message
  "Foo is not a known rule word for mule-lint"
### Changed
### Deprecated
### Removed
### Fixed
### Security

## [0.4.0] - 2014-11-02
### Added
### Changed
- The aggregators section in the result json is now an object instead of an
  array.
### Deprecated
### Removed
### Fixed
### Security

## [0.3.0] - 2014-09-16
### Added
- Added checks for having a parent element.
- Line numbers now show on output file
- Began a simple aggregator on how many loggers exist, more to come
### Changed
- The 'outputDirectory' was incorrectly named. Renamed to do just 'output'

## [0.2.0] - 2017-09-04
### Added
- The results are now written out in a json format.

## [0.1] - 2017-08-29
### Added
- Initial release, not made publicly.
### Changed
### Deprecated
### Removed
### Fixed
### Security

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
