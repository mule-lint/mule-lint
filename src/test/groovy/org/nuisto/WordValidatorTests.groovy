package org.nuisto

import org.apache.commons.lang3.StringUtils
import org.junit.Before
import org.junit.Test

class WordValidatorTests {

  def bitWords = ['bit', 'teraflop']

  WordValidator validator

  @Before
  public void setup() {
    validator = new WordValidator(bitWords)
  }

  @Test
  void knownWordsAreGood() {
    assert true == validator.isCamelCased('bitTeraflop')
  }

  @Test
  void correctCaseWithUnknownWordFails() {
    assert false == validator.isCamelCased('bitTeraflap')
  }

  @Test
  void knownWordsFailWithWrongCase() {
    assert false == validator.isCamelCased('bitteraflop')
  }

  @Test
  void anotherBadCaseTest() {
    assert false == validator.isCamelCased('bitterAFlop')
  }

  @Test
  void anotherBadCaseTestTakeTwo() {
    assert false == validator.isCamelCased('bitTerAFlop')
  }

  @Test
  void validatesFirstSubset() {
    def knownWords = ['product', 'products', 'low', 'slow']
    assert true == new WordValidator(knownWords).isCamelCased('productsSlow')
    assert true == new WordValidator(knownWords).isCamelCased('productSlow')
    assert true == new WordValidator(knownWords).isCamelCased('productsSlow')

    assert false == new WordValidator(knownWords).isCamelCased('productlow')
    assert false == new WordValidator(knownWords).isCamelCased('productslow')
  }

  @Test
  void isCamelCasedFailsWithDashesOrUnderscores() {
    def knownWords = ['product', 'products', 'low', 'slow']

    assert false == new WordValidator(knownWords).isCamelCased('product-low')
    assert false == new WordValidator(knownWords).isCamelCased('product_slow')
  }
}
