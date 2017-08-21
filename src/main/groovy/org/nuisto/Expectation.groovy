package org.nuisto

import groovy.util.logging.Slf4j

/**
 * The "has" operations set things up differently than the "having" operations
 */
@Slf4j(category = 'org.nuisto.msa')
class Expectation {
  boolean elementFound = false
  boolean passing = true
  boolean checkForAttribute = false

  String elementName
  Map<String, List<String> > attributes = [:]

  List<String> findings = []

  boolean isElementNameFound() {
    return elementFound
  }

  boolean isPassing() {
    return passing
  }

  void handleNode(NodeList nodeList, NodeChecker nodeChecker) {
    nodeList.each {
      this.handleNode(it, nodeChecker)
    }
  }

  void handleNode(Node node, NodeChecker nodeChecker) {
    if (nodeChecker.isMatch(node, elementName)) {
      elementFound = true
      log.debug 'We matched on element {}', elementName

      if (checkForAttribute) {
        passing = false
        def foundEntry = attributes.find { k, v ->

          if (!node.attributes().containsKey(k)) {
            //Node does not contain the attribute we are looking to validate
            println 'QWERQWRQWERQ'
            findings << "Element $elementName does not contain the attribute $k"
            return false
          }

          String foundValue = node.attribute(k)

          if (v == null) {
            //No value to check against, so this is purely just checking if it exists.
            return true
          }
          else {
            //Check against the list of allowed values
            if (v.contains(foundValue)) {
              return true
            }
            else {
              findings << "Element $elementName has attribute $k but $v is an invalid value"
              return false
            }
          }
        }

        passing = foundEntry != null
      }
    }
  }

  Expectation forElement(String name) {
    elementName = name
    return this
  }

  Expectation hasAttribute(String attribute) {
    return hasAttribute(attribute, (String) null)
  }

  Expectation hasAttribute(String attribute, String value) {
    return hasAttribute(attribute, value == null ? null : [value])
  }

  Expectation hasAttribute(String attribute, List<String> values) {
    checkForAttribute = true
    if (attributes.containsKey(attribute)) throw new IllegalArgumentException('Key already exists')

    attributes.put(attribute, values)

    return this
  }
}
