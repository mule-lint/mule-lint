package org.nuisto

import org.junit.Ignore
import org.junit.Test

class ExpectationTests {
  NodeChecker nodeChecker

  Expectation simpleSetup() {
    nodeChecker = new NodeChecker(['core': ['wqer']])

    Expectation expectation = new Expectation()

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

    expectation.handleNode(root.logger, nodeChecker)

    assert expectation.isElementNameFound()
  }

  @Test
  void elementAttributeIsFound() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category')

    def root = new XmlParser().parseText('<mule> <logger category=""/> </mule>')

    expectation.handleNode(root.logger, nodeChecker)

    assert expectation.isElementFound() && expectation.isPassing()
  }

  @Test
  void elementAttributeIsNotFound() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category')

    def root = new XmlParser().parseText('<mule> <logger/> </mule>')

    expectation.handleNode(root.logger, nodeChecker)

    assert expectation.isElementFound() && !expectation.isPassing()
  }

  @Test(expected = IllegalArgumentException)
  void failureIsRaisedWhenTheSameAttributeIsGivenMoreThanOnce() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category').hasAttribute('category')

    def root = new XmlParser().parseText('<mule> <logger category="odd-category"/> </mule>')

    expectation.handleNode(root.logger, nodeChecker)
  }

  @Test
  void elementAttributeIsNotFoundWhenValueIsDifferent() {
    def expectation = simpleSetup()

    //expectation.forElement('logger').hasAttribute('category').havingValue('correct-category')
    expectation.forElement('logger').hasAttribute('category', 'correct-category')

    def root = new XmlParser().parseText('<mule> <logger category="odd-category"/> </mule>')

    expectation.handleNode(root.logger, nodeChecker)

    assert expectation.isElementFound() && !expectation.isPassing()
  }

  @Test
  void elementAttributeWorksWithListsAndIsFound() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category', ['correct-category'])

    def root = new XmlParser().parseText('<mule> <logger category="correct-category"/> </mule>')

    expectation.handleNode(root.logger, nodeChecker)

    assert expectation.isElementFound() && expectation.isPassing()
  }

  @Test
  void elementAttributeWorksWithListsAndIsNotFound() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category', ['correct-category'])

    def root = new XmlParser().parseText('<mule> <logger category="odd-category"/> </mule>')

    expectation.handleNode(root.logger, nodeChecker)

    assert expectation.isElementFound() && !expectation.isPassing()
  }

  @Test(expected = IllegalArgumentException)
  void elementHasParentFailsWhenCalledTwice() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasParent('first').hasParent('second')
  }

  @Test
  void elementHasParentValidates() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasParent('mule')

    def root = new XmlParser().parseText('<mule> <logger category="odd-category"/> </mule>')

    expectation.handleNode(root.logger, nodeChecker)

    assert expectation.isElementFound() && expectation.isPassing()
  }
}
