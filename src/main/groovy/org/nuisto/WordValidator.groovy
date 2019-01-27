package org.nuisto

class WordValidator {

  def knownWords = ['bit', 'teraflop']

  def isPascalCased(String word) {
    return isCased(word, Casing.Pascal)
  }

  def isCamelCased(String word) {
    return isCased(word, Casing.Camel)
  }

  def isCased(String word, Casing casing) {

    boolean isGood = false

    boolean firstWord = true
    boolean wordBoundary = true
    String calculatedWord = ''

    for (char c in word.chars) {
      wordBoundary = false
      calculatedWord += c

      if (wordFoundInDictionary(calculatedWord)) {
        wordBoundary = true

        if (firstWord && !wordIsCapitalized(calculatedWord)) {
          isGood = true
        }
        else if (wordIsCapitalized(calculatedWord)) {
          isGood = true
        }
        else {
          isGood = false
          break
        }

        calculatedWord = ''
        wordBoundary = true
        firstWord = false
      }
    }

    return wordBoundary && isGood
  }

  /**
   * Was trying to find a better wording for this method
   * but having a hard time finding something like:
   * firstLetterIsCapitalizedAndRestOfWordIsLower
   */
  static boolean wordIsCapitalized(String word) {
    String firstChar = word[0]
    String rest = word.substring(1)

    return firstChar == firstChar.toUpperCase() && rest.toLowerCase() == rest
  }

  boolean wordFoundInDictionary(String word) {
    return knownWords.contains(word.toLowerCase())
  }
}