package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j
class ExpectationBuilder {
  def expectations = new ArrayList<Expectation>()

  ExpectationBuilder() {
  }

  void element(String name) {
    Expectation expectation = new Expectation(elementName: name)

    expectations << expectation
  }

  void hasAttribute(String s) {

  }

  List<Expectation> getExpectations() {
    return expectations
  }
}
