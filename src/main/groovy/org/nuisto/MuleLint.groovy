package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j(category = 'org.nuisto.msa')
class MuleLint {
  static void main(String [] args) {
    new MuleStaticAnalysis().run(args)
  }

  int run(String [] args) {
    def cli = new CliBuilder(usage: 'mule-lint.groovy -[hdr] -s <sourceDirectory> -r <rules>')
    // Create the list of options.
    cli.with {
      h longOpt: 'help', 'Show usage information'
      r longOpt: 'rules',     args: 1, argName: 'path', 'Required. The path to a set of rules.'
      s longOpt: 'sources',   args: 1, argName: 'sources', 'The directory name of where the source files are located, default: src/main'
      o longOpt: 'output',    args: 1, argName: 'path', 'The file name to write json results to.'
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

    if (options.s) {
      optionsModel.sourceDirectory = options.s
    }
    else {
      optionsModel.sourceDirectory = 'src/main'
    }

    if (options.o) {
      optionsModel.resultsFile = options.o
    }

    runWithModel(optionsModel)
  }

  int invoke(String rules, String sourceDirectory, Map<String, String> namespaces) {
    OptionsModel optionsModel = new OptionsModel(rules: rules, sourceDirectory: sourceDirectory, namespaces: namespaces)

    return runWithModel(optionsModel)
  }

  int runWithModel(OptionsModel model) {
    return new Runner().runWithModel(model)
  }
}
