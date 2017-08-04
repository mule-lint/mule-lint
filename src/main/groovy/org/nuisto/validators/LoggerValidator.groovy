package org.nuisto.validators

import groovy.util.logging.Slf4j
import org.nuisto.MuleNode

@Slf4j(category = 'org.nuisto.validators')
class LoggerValidator extends MuleConfigValidator {

  boolean validate(Node node) {
    boolean returnStatus = true

    if (!MuleNode.isMule('logger', node)) {
      return success = false
    }

    String category = node.attributes().category

    if (category == null || category == '') {
      log.warn('Logger with no category defined {}', node)
      returnStatus = false
    }
    //log.info 'sub-flow2 {}', root.'sub-flow'.'@name'[0]

    return returnStatus
  }
}