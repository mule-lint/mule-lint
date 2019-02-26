package org.nuisto

import org.apache.commons.lang3.StringUtils

class WordValidator {

  List<String> knownWords

  public WordValidator(List<String> wordList) {
    knownWords = wordList
  }

  boolean isPascalCased(String word) {
    return isCased(word, Casing.Pascal)
  }

  boolean isCamelCased(String word) {
    return isCased(word, Casing.Camel)
  }

  /**
   * TODO Might want to frame this differently, instead of a "isCased"
   * maybe something more like "failed" validation.
   * There could be false positives, but we don't want false negatives
   * @param word
   * @param casing
   * @return
   */
  def isCased(String word, Casing casing) {

    boolean firstWord = true
    //We would rather default to true, rather than a failure

    //Looking for failure cases
    boolean failure = findWords(word).any { currentWord ->
      if (!wordFoundInDictionary(currentWord)) {
        return true
      }
      else if (firstWord) {
        if (Casing.Camel == casing) {
          if (!(currentWord == currentWord.toLowerCase())) {
            return true
          }
        } else if (Casing.Pascal == casing) {
          if (!wordIsCapitalized(currentWord)) {
            return true
          }
        }
      }

      firstWord = false
    }

    return !failure
  }

  public List<String> findWords(String word) {
    StringUtils.splitByCharacterTypeCamelCase(word)
  }

  /**
   * Was trying to find better wording for this method
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
