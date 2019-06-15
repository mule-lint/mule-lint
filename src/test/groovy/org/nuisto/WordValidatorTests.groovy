package org.nuisto

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WordValidatorTests {

  def bitWords = ['bit', 'teraflop']

  WordValidator validator

  @BeforeEach
  public void setup() {
    validator = new WordValidator(bitWords)
  }

  @Test
  void knownWordsAreGood() {
    assert true == validator.isCamelCased('bitTeraflop')
  }

  //TODO Not sure this is what I want
  @Test
  void correctCaseWithUnknownWordFails() {
    assert false == validator.isCamelCased('bitTeraflap')
  }

  @Test
  void knownWordsFailWithWrongCase() {
    assert false == validator.isCamelCased('bitteraflop')
  }

  @Test
  void knownWordsWithBadCaseFails() {
    assert false == validator.isCamelCased('bitterAFlop')
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

  @Test
  void isPascalCasedFailsWithDashesOrUnderscores() {
    def knownWords = ['product', 'products', 'low', 'slow']

    assert true == new WordValidator(knownWords).isPascalCased('ProductLow')
  }

  @Test
  void isDashCasedSucceeds() {
    def knownWords = ['product', 'products', 'low', 'slow']

    assert true == new WordValidator(knownWords).isDashCased('product-low')
  }

  @Test
  void isDashCasedSucceedsForWordsUnknown() {
    def knownWords = ['product', 'products', 'low', 'slow']

    assert true == new WordValidator(knownWords).isDashCased('system-api')
  }

  @Test
  void isDashCasedFailsForUppercasedWords() {
    assert false == new WordValidator([]).isDashCased('System-api')
  }

  @Test
  void isDashCasedFailsForMissingDash() {
    assert false == new WordValidator(['system', 'api']).isDashCased('systemapi')
  }

  @Test
  void isDashCasedFails() {
    def knownWords = ['product', 'products', 'low', 'slow']

    assert false == new WordValidator(knownWords).isDashCased('productLow')
  }
}
