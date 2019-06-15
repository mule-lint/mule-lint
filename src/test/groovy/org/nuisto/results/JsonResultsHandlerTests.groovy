package org.nuisto.results

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nuisto.model.Infraction
import org.nuisto.model.ResultsModel
import org.skyscreamer.jsonassert.JSONAssert

class JsonResultsHandlerTests {

  @BeforeEach
  void setup() {
  }

  @AfterEach
  void cleanup() {
  }

  @Test
  void generateJsonReturnsBasicResults() {

    ResultsModel resultsModel = new ResultsModel()

    String results = new JsonResultsHandler().generateJson(resultsModel)

    String expected = '''{
    "version": "0.0.2",
    "findings": [
        
    ]
}'''

    JSONAssert.assertEquals(expected, results, false)
  }

  @Test
  void generateJsonReturnsResultsForSingleInfraction() {

    String filename = 'testing-file-name.xml'

    ResultsModel resultsModel = new ResultsModel()
    //TODO Might want to not use generics here and instead use a typed-type.
    //ie filename and line number, or infraction count ...etc
    List<Infraction> infractions = new ArrayList<>()
    infractions.add(new Infraction(element: 'foo'))

    HashMap<String, Integer> map = new HashMap<String, Integer>()
    map.put(filename, 1)
    //resultsModel.aggregationTotals.put(filename, map)
    resultsModel.expectationFindings.put(filename, infractions)

    String results = new JsonResultsHandler().generateJson(resultsModel)

    String expected = '''{
    "version": "0.0.2",
    "findings": [
        {
            "file": "testing-file-name.xml",
            "messages": [
                {
                    "message": null,
                    "category": null,
                    "lineNumber": 0,
                    "element": "foo"
                }
            ],
            "aggregations": {
                 
            }
        }
    ]
}'''

    JSONAssert.assertEquals(expected, results, false)
  }
}
