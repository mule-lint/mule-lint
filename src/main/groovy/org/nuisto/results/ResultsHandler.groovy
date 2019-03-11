package org.nuisto.results

import groovy.util.logging.Slf4j
import org.nuisto.model.OptionsModel
import org.nuisto.model.ResultsModel

@Slf4j(category = 'org.nuisto.msa')
abstract class ResultsHandler {
  void handleResults(OptionsModel optionsModel, ResultsModel resultsModel) {

    if (optionsModel.resultsFile == null) {
      def msg = 'Output file was not specified, this is probably useless with it. But looking for suggestions.'
      log.error(msg)
      throw new Exception(msg)
    }
  }

  void writeToFile(OptionsModel optionsModel, String json) {
    File file = new File(optionsModel.resultsFile)

    log.debug('Writing results to {}', file.absolutePath)

    file.write(json)
  }
}