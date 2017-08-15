package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j
class ExpectationBuilder {
  String name

  def expectations = new ArrayList<Expectation>()

  ExpectationBuilder(String name) {
    this.name = name
    Expectation expectation = new Expectation(elementName: name)

    expectations << expectation
  }

  void hasAttribute(String s) {

  }

  List<Expectation> getExpectations() {
    return expectations
  }
}
