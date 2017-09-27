package org.nuisto

import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.nuisto.aggregator.Aggregator
import org.nuisto.aggregator.FlowOccurrenceAggregator
import org.nuisto.aggregator.LoggerOccurrenceAggregator
import org.nuisto.model.Infraction
import org.nuisto.model.OptionsModel
import org.nuisto.model.ResultsModel

@Slf4j(category = 'org.nuisto.msa')
class Runner {
  ResultsHandler resultsHandler

  Runner(ResultsHandler resultsHandler) {
    this.resultsHandler = resultsHandler
  }

  int runWithModel(OptionsModel optionsModel) {
    File path = new File(optionsModel.sourceDirectory)

    if (!path.exists()) {
      log.error 'Provided directory, {}, does not exist.', path.absolutePath
      System.exit(ErrorCodes.ProvidedDirectoryDoesNotExist)
    }

    File rulesPath = new File(optionsModel.rules)

    if (!rulesPath.exists()) {
      log.error 'Provided rules, {}, does not exist.', rulesPath.absolutePath
      System.exit(ErrorCodes.ProvidedDirectoryDoesNotExist)
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

    def txtFiles = new FileNameFinder().getFileNames(path.absolutePath, '**/*.xml' /* includes */, 'pom.xml **/*.pdf' /* excludes */)

    log.info 'Found {} files', txtFiles.size()

    txtFiles.each { fileName ->
      processFile(fileName, expectations, aggregators)

      //TODO There might be times where the user wants to explicitly see "0" infractions for a file.
      //A user might like to see this so they know for sure the file was looked at.
      //It might also be able to be brought in as a calculation (if ratio of 0 no infractions to found is > than X, then fail build)
      //Could also use this as a trending chart
      if (expectations.findings.flatten().size() > 0)
        resultsModel.expectationFindings.put(fileName, expectations.findings.flatten())

      expectations.each { it.reset() }

      aggregators.each {
        resultsModel.aggregationTotals.put(fileName, it.totals)
      }

      Map<String, Integer> aggTotals = [:]
      aggregators.each { Aggregator aggregator ->
        aggTotals += aggregator.totals
      }

      resultsModel.aggregationTotals.put(fileName, aggTotals)

      aggregators.each { it.reset() }
    }

    resultsHandler.handleResults(optionsModel, resultsModel)

    return 0
  }

  def processFile(String file, List<Expectation> expectations, List<Aggregator> aggregators) {

    log.debug('Checking {}', file)

    Map<String, String> foundNamespaces = new Hashtable<String, String>()
    def parser = new PeakNamespacesXmlParser(foundNamespaces)
    def root = parser.parse(file)

    NodeChecker nodeChecker = new NodeChecker(foundNamespaces)

    if (nodeChecker.isRoot(root)) {
      log.debug 'Processing {}', file

      processEachNode(root, expectations, aggregators, nodeChecker)
    }
    else {
      log.debug 'Skipping file as not a mule file: {}', file
    }
  }

  def processEachNode(Node node, List<Expectation> expectations, List<Aggregator> aggregators, NodeChecker nodeChecker) {

    expectations.each {
      it.handleNode(node, nodeChecker)
    }

    aggregators.each {
      it.handleNode(node, nodeChecker)
    }

    node.children().each { it ->
      // There are times when the Node could be a text node and in that case, we get a String
      if (it instanceof Node) processEachNode((Node)it, expectations, aggregators, nodeChecker)
    }
  }
}
