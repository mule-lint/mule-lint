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
    int result = muleLint.run(['--rules', rulesPath] as String[])
    OptionsModel optionsModel = muleLint.modelUsed

    assert result == ErrorCodes.Success
    assert optionsModel.rules == rulesPath
  }

  @Test
  void runNeedsRulesAndIfNotProvidedThenReturnsError() {
    int result = muleLint.run(['--nothing'] as String[])
    OptionsModel optionsModel = muleLint.modelUsed

    assert result == ErrorCodes.RulesNotProvided
  }

  @Test
  void runParsesDictionaryCorrectly() {
    def dictionaryPath = 'filename.txt'

    int result = muleLint.run(['--rules', 'rules-path.txt', '--dictionary', dictionaryPath] as String[])
    OptionsModel optionsModel = muleLint.modelUsed

    assert result == ErrorCodes.Success
    assert optionsModel.dictionary == dictionaryPath
  }

  @Test
  void runParsesSourcesCorrectly() {
    def sourcesPath = 'filename.txt'

    int result = muleLint.run(['--rules', 'rules-path.txt', '--sources', sourcesPath] as String[])
    OptionsModel optionsModel = muleLint.modelUsed

    assert result == ErrorCodes.Success
    assert optionsModel.sourceDirectory == sourcesPath
  }

  @Test
  void runParsesOutputResultsPathCorrectly() {
    def outputPath = 'filename.txt'

    int result = muleLint.run(['--rules', 'rules-path.txt', '--output', outputPath] as String[])
    OptionsModel optionsModel = muleLint.modelUsed

    assert result == ErrorCodes.Success
    assert optionsModel.resultsFile == outputPath
  }
}
