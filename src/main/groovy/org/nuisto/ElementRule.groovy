package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j(category = 'org.nuisto.msa')
class ElementRule {
  String version
  ExpectationBuilder builder

  ElementRule(ExpectationBuilder builder) {
    this.builder = builder
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

    builder.element(name)

    return this
  }

  ElementRule hasAttribute(String name) {
    log.debug 'Something should have attribute of ' + name

    builder.hasAttribute(name)

    return this
  }

  ElementRule hasParent(String name) {
    log.debug 'hasParent specified ' + name

    builder.hasParent(name)

    return this
  }

  ElementRule hasPriorSibling(String name) {
    log.debug 'hasPriorSibling specified ' + name

    builder.hasPriorSibling(name)

    return this
  }

  ElementRule hasFollowingSibling(String name) {
    log.debug 'hasFollowingSibling specified ' + name

    builder.hasFollowingSibling(name)

    return this
  }

  ElementRule hasChild(String name) {
    throw new UnsupportedOperationException('Not yet implemented')
  }

  ElementRule withAttribute(String name) {
    throw new UnsupportedOperationException('Not yet implemented')
  }

  ElementRule withValue(String name) {
    throw new UnsupportedOperationException('Not yet implemented')
  }
}

