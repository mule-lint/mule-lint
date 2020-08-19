# Changelog
All notable changes to this project will be documented in this file.

## [Unreleased]
### Added
### Changed
### Deprecated
### Removed
### Fixed
### Security

## [0.5.3] - 2020-08-19
### Fixed
- See #32 - fixed the broken 0.5.2 release

## [0.5.2 BROKEN] - 2020-08-18
### Changed
- Upgraded gradle to 6.6, resolved deprecated features preparing for 7.0 release
- Better handling of missing dictionary file
- Improved the error handling of running the cli as well as the maven-plugin. There were some inconsistencies between the two.
- Improved messaging when the required `version` line is missing or otherwise invalid  
### Fixed
- Some scenarios existed where the sibling checks would not handle a NPE

## [0.5.1] - 2019-09-12
### Added
- Exclude pattern of files to ignore/exclude
- Pascal and Dash cased checking of flow names
- Raise exception thrown when `version` is not the first line or unknown
- Added failBuild option
### Changed
- Updated Gradle version
- Use the same `groovy-all` dependency that Mule uses
### Fixed
- Corrected bug where Pascal and Camel cased where not compared correctly
- Corrected bug where the last rule/line wasn't read in correctly and was ignored

## [0.5.0] - 2019-02-10
### Added
- Camel casing checks on flow and sub-flow elements.
- Command line parameter added to specify a dictionary of words to check against for camel casing.
- Now when a word is used that isn't a part of the DSL, you will get a message
  "Foo is not a known rule word for mule-lint"

## [0.4.0] - 2017-11-02
### Changed
- The aggregators section in the result json is now an object instead of an
  array.

## [0.3.0] - 2017-09-16
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
