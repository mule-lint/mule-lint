package org.nuisto

import groovy.util.logging.Slf4j
import groovy.xml.QName

@Slf4j(category = 'org.nuisto.mat')
class MuleNode {
  static String Core = 'http://www.mulesoft.org/schema/mule/core'
  Map<String, List<String> > namespaces

  MuleNode(Map<String, List<String> > namespaces) {
    this.namespaces = namespaces
  }

  boolean isRoot(Node node) {
    return isMatch(node, 'mule')
  }

  boolean isMatch(Node node, String name) {
    log.debug ('Check node {} for {}', node.name(), name)

    boolean isClass = node.name() instanceof QName


    if (isClass) {
      //TODO This logic should already be somewhere. Find it and reuse
      int prefixIndex = name.indexOf(':')

      String prefix = prefixIndex > 0 ? name[0..(prefixIndex-1)] : null
      String postfix = prefixIndex > 0 ? name[(prefixIndex+1)..-1] : name

      String namespace = prefix != null ? namespaces[prefix] : null

      boolean isNameMatch = node.name().localPart == postfix
      boolean isNamespaceMatch = node.name().namespaceURI == namespace

      log.info 'Matching {}, results isNameMatch={} isNamespaceMatch={}', name, isNamespaceMatch, isNamespaceMatch
      return isNameMatch && isNamespaceMatch
    }
    else {
      log.info 'Matching {}, results isNameMatch={}', name, node.name() == name
      return node.name() == name
    }
  }
}
