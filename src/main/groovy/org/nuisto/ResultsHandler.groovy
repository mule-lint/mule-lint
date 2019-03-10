package org.nuisto

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.nuisto.model.Infraction
import org.nuisto.model.OptionsModel
import org.nuisto.model.ResultsModel

@Slf4j(category = 'org.nuisto.msa')
class ResultsHandler {
  void handleResults(OptionsModel optionsModel, ResultsModel resultsModel) {

    if (optionsModel.resultsFile == null) {
      def msg = 'Output file was not specified, this is probably useless with it. But looking for suggestions.'
      log.error(msg)
      throw new Exception(msg)
    }

    log.info 'Found {} infractions.', resultsModel.expectationFindings.size()

    String json = generateJson(resultsModel)

    writeToFile(optionsModel, json)
  }

  private String generateJson(resultsModel) {
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

  private void writeToFile(OptionsModel optionsModel, String json) {
    File file = new File(optionsModel.resultsFile)

    log.debug('Writing results to {}', file.absolutePath)

    file.write(json)
  }
}