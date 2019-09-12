package org.nuisto

import org.junit.jupiter.api.Test
import org.nuisto.model.OptionsModel

class ElementExpectationBuilderTests {

  @Test
  void expectationsAreNotNull() {
    String elementName = 'logger'

    ExpectationBuilder builder = new ElementExpectationBuilder(elementName, null)

    Expectation expectation = builder.build()
  }

  @Test
  void nameIsNotBlank() {
    String elementName = 'logger'

    ElementExpectationBuilder builder = ElementExpectationBuilder.create(elementName, null)

    builder.hasAttribute('testing')

    Expectation expectation = builder.build()
  }

  @Test
  void worksWithHasParent() {
    ExpectationBuilder builder = new ElementExpectationBuilder('one', null)

    builder.hasParent('asdf')

    Expectation expectation = builder.build()
  }
}
