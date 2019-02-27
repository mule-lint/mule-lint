package org.nuisto

import groovy.util.logging.Slf4j
import org.nuisto.model.Infraction

/**
 *
 */
@Slf4j(category = 'org.nuisto.msa')
class FlowExpectation extends Expectation {
  Casing casing
  boolean isCaseSpecified

  WordValidator wordValidator

  FlowExpectation(WordValidator wordValidator) {
    this.wordValidator = wordValidator
    init()
  }

  void init() {
    infractions = []
    isPassing = true

    isCaseSpecified = false
  }

  void reset() {
    init()
  }

  boolean isPassing() {
    return (!isCaseSpecified) || (isCaseSpecified && isPassing)
  }

  void setCamelCased() {
    isCaseSpecified = true
    casing = Casing.Camel
  }

  void setPascalCased() {
    isCaseSpecified = true
    casing = Casing.Pascal
  }

  void setDashCased() {
    isCaseSpecified = true
    casing = Casing.Dashed
  }

  void handleNode(MuleXmlNode node) {
    if (node.isFlowesque()) {
      def flowName = node.getAttribute('name')
      log.debug 'Is flow camel cased? {}', flowName

      if (casing == Casing.Pascal && !wordValidator.isPascalCased(flowName)) {
        isPassing = false
        infractions << new Infraction(
                element: node.name,
                message: "Flow $flowName is not PascalCased",
                lineNumber: node.lineNumber,
                category: 'Casing')
      }
      else if (casing == Casing.Camel && !wordValidator.isCamelCased(flowName)) {
        isPassing = false
        infractions << new Infraction(
                element: node.name,
                message: "Flow $flowName is not camelCased",
                lineNumber: node.lineNumber,
                category: 'Casing')

      }
      else if (casing == Casing.Dashed && !wordValidator.isDashCased(flowName)) {
        isPassing = false
        infractions << new Infraction(
                element: node.name,
                message: "Flow $flowName is not dash-cased",
                lineNumber: node.lineNumber,
                category: 'Casing')

      }
      else {
        isPassing = true
      }
    }
  }
}
