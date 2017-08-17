package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j(category = 'org.nuisto.mat')
class ElementRule {
  String version
  ExpectationBuilder builder

  ElementRule(ExpectationBuilder builder) {
    println 'In constructor*****'
  }

  ElementRule version(String version) {
    log.info 'Parsing a file with the version of ' + version

    if (this.version != null) {
      def message = 'Can not set version once it has already been set'

      log.error message

      throw new Exception(message)
    }

    return this
  }

  ElementRule element(String name) {
    println 'Picked up element of ' + name

    return this
  }

  ElementRule hasAttribute(String name) {
    println 'Something should have attribute of ' + name

    return this
  }

  ElementRule hasParent(String name) {
    println 'It should have a parent of ' + name

    return this
  }

  ElementRule hasChild(String name) {
    println 'It should have child of ' + name

    return this
  }

  ElementRule withAttribute(String name) {
    println 'It should have attribute of ' + name

    return this
  }

  ElementRule withValue(String name) {
    println 'It should have value of ' + name

    return this
  }

  ElementRule havingValue(String name) {
    println 'havingValue of ' + name

    return this
  }
}

