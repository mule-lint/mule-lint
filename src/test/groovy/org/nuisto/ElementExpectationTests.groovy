package org.nuisto

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ElementExpectationTests {
  NodeChecker nodeChecker

  ElementExpectation simpleSetup() {
    nodeChecker = new NodeChecker(
      [
        'http': 'http://www.mulesoft.org/schema/mule/http',
        'doc': 'http://www.mulesoft.org/schema/mule/documentation'
      ])

    ElementExpectation expectation = new ElementExpectation()

    return expectation
  }

  @Test
  void elementNameIsNotFound() {
    def expectation = simpleSetup()

    assert false == expectation.isElementNameFound()
  }

  @Test
  void elementNameIsFound() {
    def expectation = simpleSetup()

    expectation.elementName = 'logger'

    def root = new XmlParser().parseText('<mule> <logger /> </mule>')

    expectation.handleNode(new MuleXmlNode(root.logger[0], nodeChecker))

    assert expectation.isElementNameFound()
  }

  @Test
  void elementAttributeIsFound() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category')

    def root = new XmlParser().parseText('<mule> <logger category=""/> </mule>')

    expectation.handleNode(new MuleXmlNode(root.logger[0], nodeChecker))

    assert expectation.isElementFound() && expectation.isIsPassing()
  }

  @Test
  void elementAttributeIsNotFound() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category')

    def root = new XmlParser().parseText('<mule> <logger/> </mule>')

    expectation.handleNode(new MuleXmlNode(root.logger[0], nodeChecker))

    assert expectation.isElementFound() && !expectation.isIsPassing()
  }

  @Test
  void failureIsRaisedWhenTheSameAttributeIsGivenMoreThanOnce() {
    def expectation = simpleSetup()

    Assertions.assertThrows(IllegalArgumentException.class, {
      expectation.forElement('logger').hasAttribute('category').hasAttribute('category')
    })
  }

  @Test
  void elementAttributeIsNotFoundWhenValueIsDifferent() {
    def expectation = simpleSetup()

    //expectation.forElement('logger').hasAttribute('category').havingValue('correct-category')
    expectation.forElement('logger').hasAttribute('category', 'correct-category')

    def root = new XmlParser().parseText('<mule> <logger category="odd-category"/> </mule>')

    expectation.handleNode(new MuleXmlNode(root.logger[0], nodeChecker))

    assert expectation.isElementFound() && !expectation.isIsPassing()
  }

  @Test
  void elementAttributeWorksWithListsAndIsFound() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category', ['correct-category'])

    def root = new XmlParser().parseText('<mule> <logger category="correct-category"/> </mule>')

    expectation.handleNode(new MuleXmlNode(root.logger[0], nodeChecker))

    assert expectation.isElementFound() && expectation.isIsPassing()
  }

  @Test
  void elementAttributeWorksWithListsAndIsNotFound() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category', ['correct-category'])

    def root = new XmlParser().parseText('<mule> <logger category="odd-category"/> </mule>')

    expectation.handleNode(new MuleXmlNode(root.logger[0], nodeChecker))

    assert expectation.isElementFound() && !expectation.isIsPassing()
  }

  @Test
  void elementHasParentFailsWhenCalledTwice() {
    def expectation = simpleSetup()

    Assertions.assertThrows(IllegalArgumentException.class, {
      expectation.forElement('logger').hasParent('first').hasParent('second')
    })
  }

  @Test
  void elementHasParentValidates() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasParent('mule')

    def root = new XmlParser().parseText('<mule> <logger category="odd-category"/> </mule>')

    expectation.handleNode(new MuleXmlNode(root.logger[0], nodeChecker))

    assert expectation.isElementFound() && expectation.isIsPassing()
  }

  @Test
  void elementHasPriorSibling() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasPriorSibling('until-successful')

    def root = new XmlParser().parseText('''
<mule>
  <logger />
</mule>
''')

    expectation.handleNode(new MuleXmlNode(root.logger[0], nodeChecker))

    assert expectation.isElementFound() && !expectation.isIsPassing()
  }

  @Test
  void elementHasFollowingSibling() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasPriorSibling('until-successful')

    def root = new XmlParser().parseText('''
<mule>
  <logger />
</mule>
''')

    expectation.handleNode(new MuleXmlNode(root.logger[0], nodeChecker))

    assert expectation.isElementFound() && !expectation.isIsPassing()
  }

  @Test
  void elementHasPriorSiblingThrowsExceptionForSameNames() {
    def expectation = simpleSetup()

    Assertions.assertThrows(IllegalArgumentException.class, {
      expectation.forElement('logger').hasPriorSibling('logger')
    })
  }

  @Test
  void elementHasFollowingSiblingThrowsExceptionForSameNames() {
    def expectation = simpleSetup()

    Assertions.assertThrows(IllegalArgumentException.class, {
      expectation.forElement('logger').hasFollowingSibling('logger')
    })
  }

  @Test
  void elementHasPriorSiblingPasses() {
    def expectation = simpleSetup()

    expectation.forElement('http:request').hasPriorSibling('logger')

    def root = new XmlParser().parseText('''
<mule xmlns:http="http://www.mulesoft.org/schema/mule/http"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd">
  <logger />
  <http:request />
</mule>
''')

    expectation.handleNode(new MuleXmlNode(root.children()[1], nodeChecker))

    assert expectation.isElementFound() && expectation.isIsPassing()
  }

  @Test
  void elementHasFollowingSiblingPasses() {
    def expectation = simpleSetup()

    expectation.forElement('http:request').hasFollowingSibling('logger')

    def root = new XmlParser().parseText('''
<mule xmlns:http="http://www.mulesoft.org/schema/mule/http"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd">
  <http:request />
  <logger />
</mule>
''')

    expectation.handleNode(new MuleXmlNode(root.children()[0], nodeChecker))

    assert expectation.isElementFound() && expectation.isIsPassing()
  }

  @Test
  void elementHasAttributeWorksWithNamespaces() {
    def expectation = simpleSetup()

    expectation.forElement('http:request').hasAttribute('doc:name')

    def root = new XmlParser().parseText('''
<mule xmlns:http="http://www.mulesoft.org/schema/mule/http"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:doc="http://www.mulesoft.org/schema/mule/documentation"
	xsi:schemaLocation="
http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd">
  <http:request doc:name="qwer" />
  <logger />
</mule>
''')

    expectation.handleNode(new MuleXmlNode(root.children()[0], nodeChecker))

//    assert expectation.hasAttribute('doc:name') && expectation.isIsPassing()
    assert expectation.isElementFound() && expectation.isIsPassing()
  }
}
