package org.nuisto

import org.junit.Test

class ElementExpectationBuilderTests {

  @Test
  void expectationsAreNotNull() {
    String elementName = 'logger'

    ExpectationBuilder builder = new ExpectationBuilder()

    builder.element(elementName)

    assert builder.expectations != null
  }

  @Test
  void nameIsNotBlank() {
    String elementName = 'logger'

    ExpectationBuilder builder = new ExpectationBuilder()

    builder.element(elementName)

    builder.hasAttribute('testing')

    assert builder.expectations != null
  }

  @Test
  void withTwoElementCalls_ThenShouldHaveTwoExpectations() {
    ExpectationBuilder builder = new ExpectationBuilder()

    builder.element('one')
    builder.element('two')

    assert 2 == builder.expectations.size()
  }

  @Test
  void allExpectationsAreSuccessful() {
    ExpectationBuilder builder = new ExpectationBuilder()

    builder.element('one')
    builder.element('two')

    builder.expectations.each {
      assert it.isPassing()
    }
  }

  @Test
  void worksWithHasParent() {
    ExpectationBuilder builder = new ExpectationBuilder()

    builder.element('one')
    builder.hasParent('asdf')

    builder.expectations.each {
      assert it.isPassing()
    }
  }
}
