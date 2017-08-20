package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j(category = 'org.nuisto.msa')
class ElementRule {
  String version
  ExpectationBuilder builder

  ElementRule(ExpectationBuilder builder) {
  }

  ElementRule version(String version) {
    log.info 'Version set to ' + version

    if (this.version != null) {
      def message = 'Can not set version once it has already been set'

      log.error message

      throw new Exception(message)
    }

    return this
  }

  ElementRule element(String name) {
    log.debug 'Picked up element of ' + name

    return this
  }

  ElementRule hasAttribute(String name) {
    log.debug 'Something should have attribute of ' + name

    return this
  }

  ElementRule hasParent(String name) {
    log.debug 'It should have a parent of ' + name

    return this
  }

  ElementRule hasChild(String name) {
    log.debug 'It should have child of ' + name

    return this
  }

  ElementRule withAttribute(String name) {
    log.debug 'It should have attribute of ' + name

    return this
  }

  ElementRule withValue(String name) {
    log.debug 'It should have value of ' + name

    return this
  }

  ElementRule havingValue(String name) {
    log.debug 'havingValue of ' + name

    return this
  }
}

