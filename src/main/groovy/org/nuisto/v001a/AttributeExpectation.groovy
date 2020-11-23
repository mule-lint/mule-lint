package org.nuisto.v001a

import org.nuisto.Expectation
import org.nuisto.MuleXmlNode
import org.nuisto.model.Infraction

class AttributeExpectation extends Expectation {

  String attributeName
  String [] attributeValues

  AttributeExpectation() {
    init()
  }

  void handleNode(MuleXmlNode node) {
    String elementName = node.name

    if (!node.hasAttribute(attributeName)) {
      //Node does not contain the attribute we are looking to validate
      infractions << new Infraction(
        element: elementName,
        message: "Element $elementName does not contain the attribute $attributeName",
        lineNumber: node.lineNumber,
        category: 'InvalidAttribute')
      isPassing = false
    }

    String foundValue = node.getAttribute(attributeName)

    if (attributeValues == null) {
      //No value to check against, so this is purely just checking if the attribute exists.
    }
    else {
      //Check against the list of allowed values
      if (!attributeValues.contains(foundValue)) {
        infractions << new Infraction(
          element: elementName,
          message: "Element $elementName has attribute $attributeName but $foundValue is an invalid value",
          lineNumber: node.lineNumber,
          category: 'InvalidAttribute')
        isPassing = false
      }
    }
  }
}
