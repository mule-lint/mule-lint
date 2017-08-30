package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j(category = 'org.nuisto.msa')
class ExpectationBuilder {
  Expectation currentExpectation
  List<Expectation> expectations = new ArrayList<Expectation>()

  ExpectationBuilder() {
  }

  void element(String name) {
    Expectation expectation = new Expectation(elementName: name)

    currentExpectation = expectation

    expectations << expectation
  }

  void hasAttribute(String attribute) {
    currentExpectation.hasAttribute(attribute)
  }

  void hasAttribute(String attribute, String value) {
    currentExpectation.hasAttribute(attribute, value)
  }

  void hasAttribute(String attribute, List<String> values) {
    currentExpectation.hasAttribute(attribute, values)
  }

  void hasParent(String parent) {
    currentExpectation.hasParent(parent)
  }

  List<Expectation> getExpectations() {
    return expectations
  }
}
