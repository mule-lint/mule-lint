package org.nuisto

import groovy.util.logging.Slf4j

//http://melix.github.io/blog/2015/03/sandboxing.html
import org.nuisto.*

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.SecureASTCustomizer
import org.codehaus.groovy.control.customizers.*
import org.codehaus.groovy.ast.expr.*
import static org.codehaus.groovy.syntax.Types.*

/*
ImportCustomizer: add transparent imports
ASTTransformationCustomizer: injects an AST transform
SecureASTCustomizer: restrict the groovy language to an allowed subset
*/
@Slf4j(category = 'org.nuisto.mat')
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
      constantTypesClassWhiteList = [
        Integer, Float, Long, Double, BigDecimal,
        Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE
      ].asImmutable()

      // classes who are allowed to be receivers of method calls
      // TODO Of course I don't like having java.lang.Object, but need to come back to this
      // Looks like using the receiversClassesWhiteList can be worked around easily and not bullet proof.
      receiversClassesWhiteList = [
        Math, Integer, Float, Double, Long, BigDecimal, java.lang.Object
      ].asImmutable()
    }

    return secure
  }

  void load(OptionsModel model) {
    SecureASTCustomizer secure = restrictEnvironment()

    def importCustomizer = new ImportCustomizer()
    //importCustomizer.addStaticStars Direction.class.name
    importCustomizer.addStaticStars 'java.lang.Math'

    def config = new CompilerConfiguration()
    //config.addCompilationCustomizers importCustomizer
    config.addCompilationCustomizers(importCustomizer, secure)
    config.scriptBaseClass = LoaderBaseScriptClass.class.name

    ElementRule elementRule = new ElementRule()

    def binding = new Binding([
      elementRule: elementRule
    ])

    def shell = new GroovyShell(this.class.classLoader, binding, config)

    shell.evaluate(new File(model.rules))
  }
}
