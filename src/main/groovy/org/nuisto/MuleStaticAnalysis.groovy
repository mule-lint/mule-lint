package org.nuisto

import groovy.util.logging.Slf4j
import jdk.nashorn.internal.runtime.options.Options
import org.nuisto.validators.HttpRequestValidator
import org.nuisto.validators.LoggerValidator

@Slf4j(category = 'org.nuisto.mat')
class MuleStaticAnalysis {
  static void main(String [] args) {
    new MuleStaticAnalysis().run(args)
  }

  int run(String [] args) {
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

    runWithModel(optionsModel)
  }

  int invoke(String rules, String directory, String sourceDirectory) {
    OptionsModel optionsModel = new OptionsModel(rules: rules, directory: directory, sourceDirectory: sourceDirectory)

    return runWithModel(optionsModel)
  }

  int runWithModel(OptionsModel model) {
    return new Runner().runWithModel(model)
  }
}
