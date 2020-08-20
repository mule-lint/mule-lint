package org.nuisto

import groovy.util.logging.Slf4j
import org.nuisto.aggregator.Aggregator
import org.nuisto.aggregator.FlowOccurrenceAggregator
import org.nuisto.aggregator.LoggerOccurrenceAggregator
import org.nuisto.model.OptionsModel
import org.nuisto.model.ResultsModel
import org.nuisto.results.ResultsHandler

@Slf4j(category = 'org.nuisto.msa')
class Runner {
  ResultsHandler resultsHandler

  Runner(ResultsHandler resultsHandler) {
    this.resultsHandler = resultsHandler
  }

  void runWithModel(OptionsModel optionsModel) {
    File path = new File(optionsModel.sourceDirectory)

    if (!path.exists()) {
      throw new IllegalArgumentException("Provided directory, ${path.absolutePath}, does not exist.")
    }

    File rulesPath = new File(optionsModel.rules)

    if (!rulesPath.exists()) {
      throw new IllegalArgumentException("Provided rules, ${rulesPath.absolutePath}, does not exist.")
    }

    log.debug 'Using source directory: {}', path.absolutePath
    log.debug 'Using rules: {}', new File(optionsModel.rules).absolutePath

    ResultsModel resultsModel = new ResultsModel()

    RulesLoader loader = new RulesLoader()
    List<Expectation> expectations = loader.load(optionsModel)
    List<Aggregator> aggregators = [
      new LoggerOccurrenceAggregator(),
      new FlowOccurrenceAggregator()
    ]

    String includePattern = '**/*.xml'

    def defaultExcludePatterns = [
      'pom.xml',
      '**/src/test/munit/*.xml',
      '**/target/**/*.xml',
      '**/log4j*.xml'
    ]
    // TODO Work on this a bit more
    defaultExcludePatterns.addAll(optionsModel.excludePatterns)
    String [] excludedPatterns = defaultExcludePatterns as String []

    def txtFiles = new FileNameFinder().getFileNames(path.absolutePath, includePattern, excludedPatterns.join(' '))

    //TODO Should we output some kind of "findings" when we run with the maven output? Like a junit run does?
    log.info 'Found {} files', txtFiles.size()

    boolean failBuild = false

    txtFiles.each { fileName ->
      processFile(fileName, expectations, aggregators)

      //TODO There might be times where the user wants to explicitly see "0" infractions for a file.
      //A user might like to see this so they know for sure the file was looked at.
      //It might also be able to be brought in as a calculation (if ratio of 0 no infractions to found is > than X, then fail build)
      //Could also use this as a trending chart
      //Don't want users inferring that null or missing value is '0'
      if (expectations.infractions.flatten().size() > 0) {
        failBuild = true
        resultsModel.expectationFindings.put(fileName, expectations.infractions.flatten())
      }

      log.debug 'Resetting expectations per new file.'
      expectations.each { it.reset() }

      //TODO Does a runner care about the various 'expectations' we will have?
      //Seems like it would just talk to some holder of the data and tell it the operations
      //The holder would then iterate through each collection
      //But what if we end up having a parameter on which expectations to run or exclude?
      //Then the runner would care about that .... maybe (might be another delegation)
      Map<String, Integer> aggTotals = [:]
      aggregators.each { Aggregator aggregator ->
        resultsModel.aggregationTotals.put(fileName, aggregator.totals)

        aggTotals += aggregator.totals

        aggregator.reset()
      }

      resultsModel.aggregationTotals.put(fileName, aggTotals)
    }

    resultsHandler.handleResults(optionsModel, resultsModel)

    if (optionsModel.failBuild && failBuild) {
      throw new IllegalStateException("Infractions found.")
    }
  }

  def processFile(String file, List<Expectation> expectations, List<Aggregator> aggregators) {

    log.debug('Checking {}', file)

    MuleXmlParser parser = new MuleXmlParser(file)

    if (parser.isMuleFile()) {
      log.debug 'Processing {}', file

      parser.forEachMuleNode { MuleXmlNode node ->

        expectations.each {
          it.handleNode(node)
        }

        aggregators.each {
          it.handleNode(node)
        }
      }
    }
    else {
      log.debug 'Skipping file as not a mule file: {}', file
    }
  }
}
