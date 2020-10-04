package org.nuisto

import groovy.util.logging.Slf4j
import org.nuisto.model.Infraction

/**
 * The "has" operations set things up differently than the "having" operations
 */
@Slf4j(category = 'org.nuisto.msa')
class ElementExpectation extends Expectation {
  boolean elementFound
  boolean checkForAttribute

  String parent

  String priorSibling
  String followingSibling

  String elementName
  Map<String, List<String> > attributes

  ElementExpectation() {
    elementName = null
    checkForAttribute = false
    attributes = [:]
    init()
  }

  void init() {
    infractions = []
    elementFound = false
    isPassing = true
  }

  void reset() {
    init()
  }

  boolean isElementNameFound() {
    return elementFound
  }

  void handleNode(MuleXmlNode node) {
    if (node.isMatch(elementName)) {
      elementFound = true
      log.debug 'We matched on element {}', elementName

      validateParent(node)

      validateAttributes(node)

      validatePriorSibling(node)

      validateFollowingSibling(node)
    }
  }

  ElementExpectation forElement(String name) {
    elementName = name
    return this
  }

  ElementExpectation hasAttribute(String attribute) {
    return hasAttribute(attribute, (String) null)
  }

  ElementExpectation hasAttribute(String attribute, String value) {
    return hasAttribute(attribute, value == null ? null : [value])
  }

  ElementExpectation hasAttribute(String attribute, List<String> values) {
    checkForAttribute = true
    if (attributes.containsKey(attribute)) throw new IllegalArgumentException('Key already exists')

    attributes.put(attribute, values)

    return this
  }

  ElementExpectation hasParent(String parent) {
    if (this.parent != null) throw new IllegalArgumentException('Parent already specified')

    this.parent = parent

    return this
  }

  ElementExpectation hasPriorSibling(String sibling) {
    if (this.priorSibling != null) throw new IllegalArgumentException('Prior Sibling already specified')
    if (this.elementName == sibling) throw new IllegalArgumentException('Sibling can not be the same as the element')

    this.priorSibling = sibling

    return this
  }

  ElementExpectation hasFollowingSibling(String sibling) {
    if (this.followingSibling != null) throw new IllegalArgumentException('Following Sibling already specified')
    if (this.elementName == sibling) throw new IllegalArgumentException('Sibling can not be the same as the element')

    this.followingSibling = sibling

    return this
  }

  void validateAttributes(MuleXmlNode node) {
    if (checkForAttribute) {
      isPassing = false
      def foundEntry = attributes.find { k, v ->

        if (!node.hasAttribute(k)) {
          //Node does not contain the attribute we are looking to validate

          infractions << new Infraction(
                  element: elementName,
                  message: "Element $elementName does not contain the attribute $k",
                  lineNumber: node.lineNumber,
                  category: 'InvalidAttribute')
          return false
        }

        String foundValue = node.getAttribute(k)

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
            infractions << new Infraction(
                    element: elementName,
                    message: "Element $elementName has attribute $k but $foundValue is an invalid value",
                    lineNumber: node.lineNumber,
                    category: 'InvalidAttribute')
            return false
          }
        }
      }

      isPassing = foundEntry != null
    }
  }

  void validateFollowingSibling(MuleXmlNode node) {
    if (followingSibling == null) return

    MuleXmlNode foundFollowingSibling = node.followingSibling

    if (!nodeMatches(followingSibling, foundFollowingSibling)) {
      infractions << new Infraction(
              element: elementName,
              message: "Element $elementName does not have a sibling of $followingSibling",
              lineNumber: node.lineNumber,
              category: 'InvalidSibling')

      isPassing = false
    }
    else {
      isPassing = true
    }
  }

  static boolean nodeMatches(String name, MuleXmlNode second) {
    return name != null && second.isMatch(name)
  }

  void validatePriorSibling(MuleXmlNode node) {
    if (priorSibling == null) return

    MuleXmlNode foundPriorSibling = node.priorSibling

    if (foundPriorSibling == null || !foundPriorSibling.isMatch(priorSibling)) {
      infractions << new Infraction(
              element: elementName,
              message: "Element $elementName does not have a sibling of $priorSibling",
              lineNumber: node.lineNumber,
              category: 'InvalidSibling')

      isPassing = false
    }
    else {
      isPassing = true
    }
  }

  void validateParent(MuleXmlNode node) {
    if (parent != null) {
      isPassing = false

      MuleXmlNode parentNode = node.parent

      if (!parentNode.isMatch(parent)) {
        infractions << new Infraction(
                element: elementName,
                message: "Element $elementName does not have a parent of $parent",
                lineNumber: node.lineNumber,
                category: 'InvalidParent')
      }
      else {
        isPassing = true
      }
    }
  }
}
