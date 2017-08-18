package org.nuisto

import org.junit.Ignore
import org.junit.Test

class ExpectationTests {
  Expectation simpleSetup() {
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

    expectation.handleNode(root.logger)

    assert expectation.isElementNameFound()
  }

  @Test
  void elementAttributeIsFound() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category')

    def root = new XmlParser().parseText('<mule> <logger category=""/> </mule>')

    expectation.handleNode(root.logger)

    assert expectation.isElementFound() && expectation.isPassing()
  }

  @Test
  void elementAttributeIsNotFound() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category')

    def root = new XmlParser().parseText('<mule> <logger/> </mule>')

    expectation.handleNode(root.logger)

    assert expectation.isElementFound() && !expectation.isPassing()
  }

  @Test(expected = IllegalArgumentException)
  void failureIsRaisedWhenTheSameAttributeIsGivenMoreThanOnce() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category').hasAttribute('category')

    def root = new XmlParser().parseText('<mule> <logger category="odd-category"/> </mule>')

    expectation.handleNode(root.logger)
  }

  @Test
  void elementAttributeIsNotFoundWhenValueIsDifferent() {
    def expectation = simpleSetup()

    //expectation.forElement('logger').hasAttribute('category').havingValue('correct-category')
    expectation.forElement('logger').hasAttribute('category', 'correct-category')

    def root = new XmlParser().parseText('<mule> <logger category="odd-category"/> </mule>')

    expectation.handleNode(root.logger)

    assert expectation.isElementFound() && !expectation.isPassing()
  }

  @Test
  void elementAttributeWorksWithListsAndIsFound() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category', ['correct-category'])

    def root = new XmlParser().parseText('<mule> <logger category="correct-category"/> </mule>')

    expectation.handleNode(root.logger)

    assert expectation.isElementFound() && expectation.isPassing()
  }

  @Test
  void elementAttributeWorksWithListsAndIsNotFound() {
    def expectation = simpleSetup()

    expectation.forElement('logger').hasAttribute('category', ['correct-category'])

    def root = new XmlParser().parseText('<mule> <logger category="odd-category"/> </mule>')

    expectation.handleNode(root.logger)

    assert expectation.isElementFound() && !expectation.isPassing()
  }
}
