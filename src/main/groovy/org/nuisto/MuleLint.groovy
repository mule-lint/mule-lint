package org.nuisto

import groovy.util.logging.Slf4j
import org.nuisto.exceptions.DictionaryNotProvidedException
import org.nuisto.exceptions.OptionsNotProvidedException
import org.nuisto.exceptions.RulesNotProvidedException
import org.nuisto.model.OptionsModel
import org.nuisto.results.JsonResultsHandler

@Slf4j(category = 'org.nuisto.msa')
class MuleLint {
  static void main(String [] args) {
    int result = ErrorCodes.Success

    try {
      new MuleLint().run(args)
    }
    catch (RulesNotProvidedException e) {
      log.error("Missing rules argument")
      result = ErrorCodes.RulesNotProvided
    }
    catch (OptionsNotProvidedException e) {
      log.error("Missing required options argument")
      result = ErrorCodes.OptionsNotProvided
    }
    catch (DictionaryNotProvidedException e) {
      log.error("Missing dictionary argument")
      result = ErrorCodes.DictionaryFileNotProvide
    }
    catch (Exception e) {
      log.error(e.message)
      result = ErrorCodes.GenericFailure
    }

    System.exit(result)
  }

  /**
   * This is called by the main method, so let's the cli options and we can do System.exit's here
   * @param args
   */
  void run(String [] args) {
    //TODO Need to look into updated the CliBuilder used https://dzone.com/articles/groovy-25-clibuilder-renewal

    def cli = new CliBuilder(usage: 'mule-lint.groovy [-h] -s <sourceDirectory> -r <rules> -d <dictionaryFile>')
    // Create the list of options.
    cli.with {
      d(longOpt: 'dictionary', args: 1, argName: 'file',     'A dictionary file of known words')
      h(longOpt: 'help',                                     'Show usage information')
      r(longOpt: 'rules',      args: 1, argName: 'path',     'Required. The path to a set of rules.')
      s(longOpt: 'sources',    args: 1, argName: 'sources',  'The directory name of where the source files are located, default: src/main')
      o(longOpt: 'output',     args: 1, argName: 'path',     'The file name to write json results to.')
      _(longOpt: 'exclude',    args: 1, argName: 'pattern',  'Patterns to exclude (i.e. **/*.doc) uses FileNameFinder')
      _(longOpt: 'fail-build',                                'Whether to fail the build, just the existing of this key causes a failure, no need to set it to anything.')
    }

    def options = cli.parse(args)
    if (!options) {
      throw new OptionsNotProvidedException()
    }

    // Show usage text when -h or --help option is used.
    if (options.h) {
      cli.usage()
      return
    }

    def optionsModel = new OptionsModel()

    optionsModel.rules = options.r ? options.r : null
    optionsModel.sourceDirectory = options.s ? options.s : null
    optionsModel.dictionary = options.d ? options.d : null
    optionsModel.resultsFile = options.o ? options.o : null
    optionsModel.excludePatterns = options.excludes ? options.excludes : null
    optionsModel.failBuild = options.hasOption('fail-build')

    runWithModel(optionsModel)
  }

  /**
   * This is "invoked" by the maven-plugin
   * @param failBuild
   * @param dictionary
   * @param rules
   * @param sourceDirectory
   * @param outputFile
   * @param excludePatterns
   * @param namespaces
   */
  void invoke(boolean failBuild, String dictionary, String rules, String sourceDirectory, String outputFile, String [] excludePatterns, Map<String, String> namespaces) {
    OptionsModel optionsModel = new OptionsModel(
            failBuild: failBuild,
            dictionary: dictionary,
            rules: rules,
            resultsFile: outputFile,
            sourceDirectory: sourceDirectory,
            excludePatterns: excludePatterns,
            namespaces: namespaces
    )

    runWithModel(optionsModel)
  }

  /**
   * This method is shared between the cli and the maven plugin, so throw exceptions here and let each one handle
   * it appropriately.
   *
   * @param optionsModel
   */
  void runWithModel(OptionsModel optionsModel) {

    if (!optionsModel.rules) {
      throw new RulesNotProvidedException()
    }

    if (!optionsModel.sourceDirectory) {
      optionsModel.sourceDirectory = 'src/main'
    }

    if (!optionsModel.dictionary) {
      throw new DictionaryNotProvidedException()
    }

    new Runner(new JsonResultsHandler()).runWithModel(optionsModel)
  }
}
