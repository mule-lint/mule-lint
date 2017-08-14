package org.nuisto

import groovy.util.logging.Slf4j
import org.nuisto.validators.HttpRequestValidator
import org.nuisto.validators.LoggerValidator

@Slf4j(category = 'org.nuisto.mat')
class Runner {
  int runWithModel(OptionsModel optionsModel) {
    File path = new File(optionsModel.directory, optionsModel.sourceDirectory)

    if (!path.exists()) {
      log.error 'Provided directory, {}, does not exist.', path.absolutePath
      System.exit(ErrorCodes.ProvidedDirectoryDoesNotExist)
    }

    log.info 'Using directory: {}', path.absolutePath
    log.info 'Using rules: {}', new File(optionsModel.rules).absolutePath

    RulesLoader loader = new RulesLoader()
    loader.load(optionsModel)

    def txtFiles = new FileNameFinder().getFileNames(path.absolutePath, '**/*.xml' /* includes */, 'pom.xml **/*.pdf' /* excludes */)

    log.info 'Found {} files', txtFiles.size()

    txtFiles.each {
      processFile(it)
    }

    return 0
  }

  def processFile(String file) {

    log.debug('Checking {}', file)

    def root = new XmlParser().parse(file)

    if (MuleNode.isRoot(root)) {
      log.info 'Processing {}', file

      processEachNode(root)
    }
    else {
      log.debug 'Skipping file as not a mule file: {}', file
    }
  }

  def processEachNode(Node node) {
    if (MuleNode.isLogger(node)) {
      new LoggerValidator().validate(node)
    } else if (MuleNode.isHttpRequest(node)) {
      new HttpRequestValidator().validate(node)
    }
    else {
      node.children().each { Node it -> processEachNode(it) }
    }
  }
}
