package org.nuisto.results

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.nuisto.model.Infraction
import org.nuisto.model.OptionsModel
import org.nuisto.model.ResultsModel

@Slf4j(category = 'org.nuisto.msa')
class JsonResultsHandler extends ResultsHandler {
  void handleResults(OptionsModel optionsModel, ResultsModel resultsModel) {

    //Let the super class take care of the common validation
    super.handleResults(optionsModel, resultsModel)

    log.info 'Found {} infractions.', resultsModel.expectationFindings.size()

    String json = generateJson(resultsModel)

    writeToFile(optionsModel, json)
  }

  private String generateJson(ResultsModel resultsModel) {
    def json = new JsonBuilder()
    json {
      version '0.0.2'

      findings resultsModel.expectationFindings.collect { String fileName, List<Infraction> infractions ->
        [
          file        : fileName,
          messages    : infractions,

          aggregations: {
            resultsModel.aggregationTotals[fileName].each { aggregateName, value ->
              "$aggregateName" value
            }
          }
        ]
      }
    }

    json.toPrettyString()
  }
}
