package org.nuisto

import org.junit.Test
import org.junit.Ignore

class WordValidatorTests {
  @Test
  void knownWordsIsGood() {
    assert true == new WordValidator().isCamelCased('bitTeraflop')
  }

  @Test
  void correctCaseWithUnknownWordFails() {
    assert false == new WordValidator().isCamelCased('bitTeraflap')
  }

  @Test
  void knownWordsFailWithWrongCase() {
    assert false == new WordValidator().isCamelCased('bitteraflop')
  }

  @Test
  void anotherBadCaseTest() {
    assert false == new WordValidator().isCamelCased('bitterAFlop')
  }

  @Test
  void anotherBadCaseTestTakeTwo() {
    assert false == new WordValidator().isCamelCased('bitTerAFlop')
  }
}


