package org.nuisto.results

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nuisto.model.OptionsModel
import org.nuisto.model.ResultsModel
import org.nuisto.results.ResultsHandler

class JsonResultsHandlerTests {
  String filepath = 'results-handler-tests-output.txt'

  @BeforeEach
  void setup() {
  }

  @AfterEach
  void cleanup() {
    new File(filepath).delete()
  }

  @Test
  void generateJsonReturnsBasicResults() {

    ResultsModel resultsModel = new ResultsModel()

    String results = new JsonResultsHandler().generateJson(resultsModel)

    //TODO This has to be an exact match and of course not really needed.
    //We need to have a common parse of the response and assert on the object graph
    String expected = '''{
    "version": "0.0.2",
    "findings": [
        
    ]
}'''

    assert expected == results
  }
}
