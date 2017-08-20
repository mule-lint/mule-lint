package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j(category = 'org.nuisto.msa')
class Runner {
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

    log.info 'Using directory: {}', path.absolutePath
    log.info 'Using rules: {}', new File(optionsModel.rules).absolutePath

    RulesLoader loader = new RulesLoader()
    List<Expectation> expectations = loader.load(optionsModel)

    def txtFiles = new FileNameFinder().getFileNames(path.absolutePath, '**/*.xml' /* includes */, 'pom.xml **/*.pdf' /* excludes */)

    log.info 'Found {} files', txtFiles.size()

    txtFiles.each {
      processFile(it, expectations)
    }

    return 0
  }

  def processFile(String file, List<Expectation> expectations) {

    log.debug('Checking {}', file)

    Map<String, String> foundNamespaces = new Hashtable<String, String>()
    def slurper = new PeakNamespacesXmlParser(foundNamespaces)
    def root = slurper.parse(file)

    NodeChecker nodeChecker = new NodeChecker(foundNamespaces)

    if (nodeChecker.isRoot(root)) {
      log.info 'Processing {}', file

      processEachNode(root, expectations)
    }
    else {
      log.debug 'Skipping file as not a mule file: {}', file
    }
  }

  def processEachNode(Node node, List<Expectation> expectations, NodeChecker nodeChecker) {

    expectations.each {
      it.handleNode(node, nodeChecker)
    }

    node.children().each { Node it -> processEachNode(it, expectations, nodeChecker) }
  }
}
