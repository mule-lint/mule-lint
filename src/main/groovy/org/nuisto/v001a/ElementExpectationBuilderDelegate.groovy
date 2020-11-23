package org.nuisto.v001a

import org.nuisto.Expectation
import org.nuisto.ExpectationBuilder
import org.nuisto.model.OptionsModel

class ElementExpectationBuilderDelegate extends ExpectationBuilder {
  String elementName
  String priorSibling
  String followingSibling

  ElementExpectation expectation
  AttributeExpectationBuilderDelegate [] attributeExpectationBuilders
  OptionsModel optionsModel


  ElementExpectationBuilderDelegate(String name, OptionsModel optionsModel) {
    elementName = name
    this.optionsModel = optionsModel

    attributeExpectationBuilders = []
  }

  Expectation build() {
    expectation = new ElementExpectation(elementName: elementName)

    if (priorSibling) expectation.hasPriorSibling(priorSibling)
    if (followingSibling) expectation.hasFollowingSibling(followingSibling)

    attributeExpectationBuilders.each {
      expectation.addAttributeExpectation(it.build())
    }

    return expectation
  }

  void attribute(String name, Closure closure) {
    def builderDelegate = new AttributeExpectationBuilderDelegate(name, optionsModel)
    closure.delegate = builderDelegate
    closure.call()

    attributeExpectationBuilders += builderDelegate
  }

  void hasPriorSibling(String sibling) {
    this.priorSibling = sibling
  }

  void hasFollowingSibling(String sibling) {
    this.followingSibling = sibling
  }

  void excludedFrom(String value) {

  }
}
