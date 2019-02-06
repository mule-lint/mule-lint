package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j(category = 'org.nuisto.msa')
class ExpectationBuilder {
  ElementExpectation currentExpectation
  List<ElementExpectation> expectations = new ArrayList<ElementExpectation>()

  ExpectationBuilder() {
  }

  void element(String name) {
    ElementExpectation expectation = new ElementExpectation(elementName: name)

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

  void hasPriorSibling(String sibling) {
    currentExpectation.hasPriorSibling(sibling)
  }

  void hasFollowingSibling(String sibling) {
    currentExpectation.hasFollowingSibling(sibling)
  }

  List<ElementExpectation> getExpectations() {
    return expectations
  }
}
