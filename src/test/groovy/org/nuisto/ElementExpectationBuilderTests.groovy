package org.nuisto

import org.junit.jupiter.api.Test

class ElementExpectationBuilderTests {

  @Test
  void expectationsAreNotNull() {
    String elementName = 'logger'

    ExpectationBuilder builder = new ElementExpectationBuilder(elementName)

    List<Expectation> expectations = []

    assert builder.addBuiltExpectationTo(expectations) != null
  }

  @Test
  void nameIsNotBlank() {
    String elementName = 'logger'

    ExpectationBuilder builder = new ElementExpectationBuilder(elementName)

    builder.hasAttribute('testing')

    List<Expectation> expectations = []

    assert builder.addBuiltExpectationTo(expectations) != null
  }

  @Test
  void worksWithHasParent() {
    ExpectationBuilder builder = new ElementExpectationBuilder('one')

    builder.hasParent('asdf')

    List<Expectation> expectations = []

    builder.addBuiltExpectationTo(expectations)
    assert expectations.size() == 1
  }
}
