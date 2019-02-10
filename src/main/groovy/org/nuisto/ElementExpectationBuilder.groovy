package org.nuisto

import org.nuisto.model.OptionsModel

import javax.swing.text.html.Option

class ElementExpectationBuilder extends ExpectationBuilder {
  ElementExpectation expectation
  OptionsModel optionsModel


  ElementExpectationBuilder(String name, OptionsModel optionsModel) {
    this.expectation = new ElementExpectation(elementName: name)
    this.optionsModel = optionsModel
  }

  public void addBuiltExpectationTo(List<Expectation> expectationList) {
    expectationList.add(expectation)
  }

  ElementExpectationBuilder hasAttribute(String attribute) {
    expectation.hasAttribute(attribute)

    return this
  }

  ElementExpectationBuilder hasAttribute(String attribute, String value) {
    expectation.hasAttribute(attribute, value)

    return this
  }

  ElementExpectationBuilder hasAttribute(String attribute, List<String> values) {
    expectation.hasAttribute(attribute, values)

    return this
  }

  ElementExpectationBuilder hasParent(String parent) {
    expectation.hasParent(parent)

    return this
  }

  ElementExpectationBuilder hasPriorSibling(String sibling) {
    expectation.hasPriorSibling(sibling)

    return this
  }

  ElementExpectationBuilder hasFollowingSibling(String sibling) {
    expectation.hasFollowingSibling(sibling)

    return this
  }
}
