package org.nuisto

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nuisto.model.OptionsModel
import org.nuisto.model.ResultsModel

class ResultsHandlerTests {
  String filepath = 'results-handler-tests-output.txt'

  @BeforeEach
  public void setup() {
  }

  @AfterEach
  public void cleanup() {
    new File(filepath).delete()
  }

  @Test
  public void foo() {

    OptionsModel optionsModel = new OptionsModel()
    ResultsModel resultsModel = new ResultsModel()

    new ResultsHandler().handleResults(optionsModel, resultsModel)
  }
}
