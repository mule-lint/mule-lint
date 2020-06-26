package org.nuisto

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nuisto.model.OptionsModel

class MuleLintTest {
  SpyMuleLint muleLint

  @BeforeEach
  public void setup() {
    muleLint = new SpyMuleLint()
  }

  @Test
  void runNeedsRules() {
    def rulesPath = 'rules-path.txt'
    muleLint.run(['--rules', rulesPath] as String[])
    OptionsModel optionsModel = muleLint.modelUsed

    assert rulesPath == optionsModel.rules
  }

  @Test
  void runNeedsRulesAndIfNotProvidedThenReturnsError() {
    muleLint.run(['--nothing'] as String[])
    OptionsModel optionsModel = muleLint.modelUsed
  }

  @Test
  void runParsesDictionaryCorrectly() {
    def dictionaryPath = 'filename.txt'

    muleLint.run(['--rules', 'rules-path.txt', '--dictionary', dictionaryPath] as String[])
    OptionsModel optionsModel = muleLint.modelUsed

    assert dictionaryPath == optionsModel.dictionary
  }

  @Test
  void runParsesSourcesCorrectly() {
    def sourcesPath = 'filename.txt'

    muleLint.run(['--rules', 'rules-path.txt', '--sources', sourcesPath] as String[])
    OptionsModel optionsModel = muleLint.modelUsed

    assert sourcesPath == optionsModel.sourceDirectory
  }

  @Test
  void runParsesOutputResultsPathCorrectly() {
    def outputPath = 'filename.txt'

    muleLint.run(['--rules', 'rules-path.txt', '--output', outputPath] as String[])
    OptionsModel optionsModel = muleLint.modelUsed

    assert outputPath == optionsModel.resultsFile
  }

  @Test
  void runParsesExcludeOptionCorrectly() {
    muleLint.run(['--rules', 'rules-path.txt', '--exclude', 'asdf', '--exclude', 'qwer'] as String[])
    OptionsModel optionsModel = muleLint.modelUsed

    assert 2 == optionsModel.excludePatterns.size()
  }
}
