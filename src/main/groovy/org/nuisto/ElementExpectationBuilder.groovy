package org.nuisto

class ElementExpectationBuilder extends ExpectationBuilder {

  ElementExpectationBuilder(String name) {
    ElementExpectation expectation = new ElementExpectation(elementName: name)

    currentExpectation = expectation
  }

  public void addBuiltExpectationTo(List<Expectation> expectationList) {
    expectationList.add(currentExpectation)
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
}
