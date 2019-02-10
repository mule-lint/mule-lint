package org.nuisto

import groovy.util.logging.Slf4j
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.ImportCustomizer
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.nuisto.model.OptionsModel

import static org.codehaus.groovy.syntax.Types.*

/*
http://melix.github.io/blog/2015/03/sandboxing.html

ImportCustomizer: add transparent imports
ASTTransformationCustomizer: injects an AST transform
SecureASTCustomizer: restrict the groovy language to an allowed subset
*/
@Slf4j(category = 'org.nuisto.msa')
class RulesLoader {
  SecureASTCustomizer restrictEnvironment() {

    def secure = new SecureASTCustomizer()
    secure.with {
      closuresAllowed = false
      methodDefinitionAllowed = false
      importsWhitelist = []
      staticImportsWhitelist = []
      staticStarImportsWhitelist = ['java.lang.Math']

      // language tokens allowed
      tokensWhitelist = [
        PLUS, MINUS, MULTIPLY, DIVIDE, MOD, POWER, PLUS_PLUS, MINUS_MINUS,
        COMPARE_EQUAL, COMPARE_NOT_EQUAL, COMPARE_LESS_THAN, COMPARE_LESS_THAN_EQUAL,
        COMPARE_GREATER_THAN, COMPARE_GREATER_THAN_EQUAL
      ].asImmutable()

      // types allowed to be used (including primitive types)
      constantTypesClassesWhiteList = [
        Integer, Float, Long, Double, BigDecimal,
        Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE
      ].asImmutable()

      // classes who are allowed to be receivers of method calls
      // TODO Of course I don't like having java.lang.Object, but need to come back to this
      // Looks like using the receiversClassesWhiteList can be worked around easily and not bullet proof.
      receiversClassesWhiteList = [
        Math, Integer, Float, Double, Long, BigDecimal, java.lang.Object, Object
      ].asImmutable()
    }

    return secure
  }

  List<ElementExpectation> load(OptionsModel model) {
    SecureASTCustomizer secure = restrictEnvironment()

    def importCustomizer = new ImportCustomizer()
    importCustomizer.addStaticStars 'java.lang.Math'
    importCustomizer.addStaticStars NodeChecker.class.name

    def config = new CompilerConfiguration()
    config.addCompilationCustomizers importCustomizer
    //config.addCompilationCustomizers(importCustomizer, secure)
    config.scriptBaseClass = LoaderBaseScriptClass.class.name

    List<Expectation> expectations = []

    def binding = new Binding([
            expectations: expectations,
            optionsModel: model
    ])

    def shell = new GroovyShell(this.class.classLoader, binding, config)

    shell.evaluate(new File(model.rules))

    return expectations
  }
}
