package org.nuisto

import groovy.util.logging.Slf4j
import groovy.xml.QName

@Slf4j
class MuleNode {
  private static final String CoreNamespace = 'http://www.mulesoft.org/schema/mule/core'
  private static final String HttpNamespace = 'http://www.mulesoft.org/schema/mule/http'

  static boolean isRoot(Node node) {
    return isMule('mule', node)
  }

  static boolean isLogger(Node node) {
    return isMule('logger', node)
  }

  static boolean isUntilSuccessful(Node node) {
    return isMule('until-successful', node)
  }

  static boolean isHttpRequest(Node node) {
    return isMule('request', node, HttpNamespace)
  }

  static boolean isMule(String name, Node node, String namespace = CoreNamespace) {
    log.debug ('Check node {} for {}', node.name(), name)

    boolean isClass = node.name() instanceof QName

    if (isClass) {
      boolean isNameMatch = node.name().localPart == name
      boolean isNamespaceMatch = node.name().namespaceURI == namespace

      return isClass && isNameMatch && isNamespaceMatch
    }
    else {
      return false
    }
  }
}
