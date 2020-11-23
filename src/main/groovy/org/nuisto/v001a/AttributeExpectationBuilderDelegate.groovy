package org.nuisto.v001a

import org.nuisto.model.OptionsModel

enum Case {
  Lower,
  Upper,
  Camel,
  Snake
}

class AttributeExpectationBuilderDelegate {
  static String cased = 'cased'

  AttributeExpectation expectation
  String name
  String [] values
  String valueMatching
  Case attributeValueCase

  // TODO I'm not so sure we need the options model
  AttributeExpectationBuilderDelegate(String name, OptionsModel optionsModel) {
    this.name = name
  }

  AttributeExpectation build() {
    expectation = new AttributeExpectation(
      attributeName: name,
      attributeValues: values
    )

    if (valueMatching) expectation.valueMatchingPattern = valueMatching

    return expectation
  }

  void value(String str) {
    value([str])
  }

  void value(String [] values) {
    this.values = values
  }

  void valueMatching(String str) {
    valueMatching = str
  }

  void lower(String type) {
    if (type == cased)
      attributeValueCase = Case.Lower
    else
      throw new IllegalArgumentException('Unknown clause: ' + type)
  }
}
