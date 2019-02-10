package org.nuisto

import groovy.util.logging.Slf4j
import org.nuisto.model.Infraction

/**
 *
 */
@Slf4j(category = 'org.nuisto.msa')
class FlowExpectation extends Expectation {
  boolean isCaseSpecified
  boolean isCamelCased
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

  void setCamelCased(boolean value) {
    isCaseSpecified = true
    isCamelCased = value
  }

  void handleNode(MuleXmlNode node) {
    if (node.isFlowesque()) {
      def flowName = node.getAttribute('name')
      log.debug 'Is flow camel cased? {}', flowName

      if (!wordValidator.isCamelCased(flowName)) {
        isPassing = false
        infractions << new Infraction(
                element: node.name,
                message: "Flow $flowName is not camelCased",
                lineNumber: node.lineNumber,
                category: 'Casing')
      }
      else {
        isPassing = true
      }
    }
  }
}
