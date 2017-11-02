package org.nuisto

import groovy.util.logging.Slf4j
import org.nuisto.model.Infraction

/**
 * The "has" operations set things up differently than the "having" operations
 */
@Slf4j(category = 'org.nuisto.msa')
class Expectation {
  boolean elementFound
  boolean passing
  boolean checkForAttribute

  String parent

  String priorSibling
  String followingSibling

  String elementName
  Map<String, List<String> > attributes

  List<Infraction> findings

  Expectation() {
    elementName = null
    checkForAttribute = false
    attributes = [:]
    init()
  }

  void init() {
    findings = []
    elementFound = false
    passing = true
  }

  /**
   * We have to have some notion of "resetting" based upon a new file.
   *
   * An expectation is based on a file, when we have a new file, then the expectation should be reset.
   *
   * Might turn this into more like event based "onNewFile", but I'm not sure about that.
   */
  void reset() {
    log.debug 'Resetting expectation per new file.'
    init()
  }

  boolean isElementNameFound() {
    return elementFound
  }

  boolean isPassing() {
    return passing
  }

  void handleNode(Node node, NodeChecker nodeChecker) {
    if (nodeChecker.isMatch(node, elementName)) {
      elementFound = true
      log.debug 'We matched on element {}', elementName

      validateParent(node, nodeChecker)

      validateAttributes(node)

      validatePriorSibling(node, nodeChecker)

      validateFollowingSibling(node, nodeChecker)
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

  Expectation hasParent(String parent) {
    if (this.parent != null) throw new IllegalArgumentException('Parent already specified')

    this.parent = parent

    return this
  }

  Expectation hasPriorSibling(String sibling) {
    if (this.priorSibling != null) throw new IllegalArgumentException('Prior Sibling already specified')
    if (this.elementName == sibling) throw new IllegalArgumentException('Sibling can not be the same as the element')

    this.priorSibling = sibling

    return this
  }

  Expectation hasFollowingSibling(String sibling) {
    if (this.followingSibling != null) throw new IllegalArgumentException('Following Sibling already specified')
    if (this.elementName == sibling) throw new IllegalArgumentException('Sibling can not be the same as the element')

    this.followingSibling = sibling

    return this
  }

  void validateAttributes(Node node) {
    if (checkForAttribute) {
      passing = false
      def foundEntry = attributes.find { k, v ->

        if (!node.attributes().containsKey(k)) {
          //Node does not contain the attribute we are looking to validate

          findings << new Infraction(
                  element: elementName,
                  message: "Element $elementName does not contain the attribute $k",
                  lineNumber: findLineNumber(node),
                  category: 'InvalidAttribute')
          return false
        }

        String foundValue = node.attribute(k)

        if (v == null) {
          //No value to check against, so this is purely just checking if the attribute exists.
          return true
        }
        else {
          //Check against the list of allowed values
          if (v.contains(foundValue)) {
            return true
          }
          else {
            findings << new Infraction(
                    element: elementName,
                    message: "Element $elementName has attribute $k but $v is an invalid value",
                    lineNumber: findLineNumber(node),
                    category: 'InvalidAttribute')
            return false
          }
        }
      }

      passing = foundEntry != null
    }
  }

  int findLineNumber(Node node) {
    //The _msaLineNumber is set in the "PeakNamespacesXmlParser" class
    String lineNumber = node.@_msaLineNumber
    lineNumber == null ? -1 : lineNumber.toInteger()
  }

  void validateFollowingSibling(Node node, NodeChecker nodeChecker) {
    if (followingSibling == null) return

    def siblings = node.parent().children()
    def thisNodeIndex = siblings.indexOf(node)

    Node foundFollowingSibling = siblings[thisNodeIndex + 1]

    if (foundFollowingSibling == null || !nodeChecker.isMatch(foundFollowingSibling, followingSibling)) {
      findings << new Infraction(
              element: elementName,
              message: "Element $elementName does not have a sibling of $followingSibling",
              lineNumber: findLineNumber(node),
              category: 'InvalidSibling')

      passing = false
    }
    else {
      passing = true
    }
  }

  void validatePriorSibling(Node node, NodeChecker nodeChecker) {
    if (priorSibling == null) return

    def siblings = node.parent().children()
    def thisNodeIndex = siblings.indexOf(node)

    Node foundPriorSibling = siblings[thisNodeIndex - 1]

    if (foundPriorSibling == null || !nodeChecker.isMatch(foundPriorSibling, priorSibling)) {
      findings << new Infraction(
              element: elementName,
              message: "Element $elementName does not have a sibling of $priorSibling",
              lineNumber: findLineNumber(node),
              category: 'InvalidSibling')

      passing = false
    }
    else {
      passing = true
    }
  }

  void validateParent(Node node, NodeChecker nodeChecker) {
    if (parent != null) {
      passing = false

      if (!nodeChecker.isMatch(node.parent(), parent)) {
        findings << new Infraction(
                element: elementName,
                message: "Element $elementName does not have a parent of $parent",
                lineNumber: findLineNumber(node),
                category: 'InvalidParent')
      }
      else {
        passing = true
      }
    }
  }
}
