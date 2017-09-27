package org.nuisto

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.nuisto.model.Infraction
import org.nuisto.model.OptionsModel
import org.nuisto.model.ResultsModel

@Slf4j(category = 'org.nuisto.msa')
class ResultsHandler {
  void handleResults(OptionsModel optionsModel, ResultsModel resultsModel) {

    //TODO The runner should run and collect the results. Returning the results to the caller. The caller
    //is responsible for handling the results (i.e. writing to a file)

    log.info 'Found {} infractions.', resultsModel.expectationFindings.size()

    def json = new JsonBuilder()
    json {
      version '0.0.2'

      findings resultsModel.expectationFindings.collect { String fileName, List<Infraction> infractions ->
        [
          file    : fileName,
          messages: infractions,

          aggregations: {
            resultsModel.aggregationTotals[fileName].each { aggregateName, value ->
              "$aggregateName" value
            }
          }
        ]
      }
    }

    if (optionsModel.resultsFile != null) {
      File file = new File(optionsModel.resultsFile)

      log.debug('Writing results to {}', file.absolutePath)

      file.write(json.toPrettyString())
    }
    else {
      def msg = 'Output file was not specified, this is probably useless with it. But looking for suggestions.'
      log.error(msg)
      System.err.println msg
    }
  }
}