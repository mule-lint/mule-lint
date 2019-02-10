package org.nuisto

import org.junit.jupiter.api.Test

class ElementExpectationBuilderTests {

  @Test
  void expectationsAreNotNull() {
    String elementName = 'logger'

    ExpectationBuilder builder = new ElementExpectationBuilder(elementName, null)

    List<Expectation> expectations = []

    builder.addBuiltExpectationTo(expectations)
    assert expectations.size() == 1
  }

  @Test
  void nameIsNotBlank() {
    String elementName = 'logger'

    ExpectationBuilder builder = new ElementExpectationBuilder(elementName, null)

    builder.hasAttribute('testing')

    List<Expectation> expectations = []

    builder.addBuiltExpectationTo(expectations)
    assert expectations.size() == 1
  }

  @Test
  void worksWithHasParent() {
    ExpectationBuilder builder = new ElementExpectationBuilder('one', null)

    builder.hasParent('asdf')

    List<Expectation> expectations = []

    builder.addBuiltExpectationTo(expectations)
    assert expectations.size() == 1
  }
}
