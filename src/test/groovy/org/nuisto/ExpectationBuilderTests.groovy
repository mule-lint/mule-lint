package org.nuisto

import org.junit.Test

class ExpectationBuilderTests {

  @Test
  void foo() {
    String elementName = 'logger'

    ExpectationBuilder builder = new ExpectationBuilder(elementName)

    builder.hasAttribute('testing')

    assert builder.expectations != null
  }
}
