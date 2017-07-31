package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j(category = 'org.nuisto.mat')
class MuleStaticAnalysis {
  static void main(String [] args) {
    new MuleStaticAnalysis().run(args)
  }

  def run(args) {
    def cli = new CliBuilder(usage: 'mule-static-analysis.groovy -[hdr] -d <directory> -r <rules>')
    // Create the list of options.
    cli.with {
      h longOpt: 'help', 'Show usage information'
      r longOpt: 'rules', args: 1, argName: 'path', 'Required. The path to a set of rules.'
      d longOpt: 'directory', args: 1, argName: 'directory', 'The name of the directory to process.'
      s longOpt: 'sources', args: 1, argName: 'sources', 'The directory name of where the source files are located, default: src'
    }

    def options = cli.parse(args)
    if (!options) {
      return
    }

    // Show usage text when -h or --help option is used.
    if (options.h) {
      cli.usage()
      return
    }

    def optionsModel = new OptionsModel()

    if (!options.r) {
      log.error 'Rules must be provided'
      System.exit(ErrorCodes.RulesNotProvided)
    }
    else {
      optionsModel.rules = options.r
    }

    if (options.d) {
      optionsModel.directory = options.directory
    }
    else {
      optionsModel.directory = new File('.').getAbsoluteFile().getParent()
    }

    if (options.s) {
      optionsModel.sourceDirectory = options.s
    }
    else {
      optionsModel.sourceDirectory = 'src'
    }

    File path = new File(optionsModel.directory, optionsModel.sourceDirectory)

    if (!path.exists()) {
      log.error 'Provided directory, %s, does not exist.', path.absolutePath
      System.exit(ErrorCodes.ProvidedDirectoryDoesNotExist)
    }

    log.info 'Using directory: %s', path.absolutePath

    def txtFiles = new FileNameFinder().getFileNames(path.absolutePath, '**/*.xml' /* includes */, 'pom.xml **/*.pdf' /* excludes */)

    log.info 'Found %s files', txtFiles.size()

    txtFiles.each {
      processFile(it)
    }
  }

  def processFile(String file) {

    log.info('Should be processing %s', file)

    def data = new XmlSlurper().parse(file)

    log.info data.toString()
  }
}
