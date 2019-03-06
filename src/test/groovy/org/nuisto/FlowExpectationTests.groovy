package org.nuisto

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FlowExpectationTests {
  FlowExpectation expectation

  NodeChecker nodeChecker

  @BeforeEach
  public void setup() {
    WordValidator validator = new WordValidator(['update', 'flow'])
    expectation = new FlowExpectation(validator)

    nodeChecker = new NodeChecker(['http': 'http://www.mulesoft.org/schema/mule/http'])
  }

  @Test
  public void isPassingIsTrueWhenNothingHappens() {
    expectation.isPassing()
  }

  @Test
  public void isPassingIsTrueByDefaultEvenWhenCaseIsSpecified_RatherHaveFalsePositivesThanFalseNegatives() {
    expectation.setCamelCased()
    expectation.isPassing()
  }

  @Test
  public void isPassingIsTrueWhenFlowNameMatches() {

    def root = new XmlParser().parseText('''
<mule xmlns:http="http://www.mulesoft.org/schema/mule/http"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd">
  <flow name="updateFlow">
  </flow>
  <http:request />
</mule>
''')

    expectation.setCamelCased()

    expectation.handleNode(new MuleXmlNode(root.children()[0], nodeChecker))

    assert expectation.isPassing()
  }

  @Test
  public void isPassingIsFalseWhenUnknownWordsAreFound() {

    def root = new XmlParser().parseText('''
<mule xmlns:http="http://www.mulesoft.org/schema/mule/http"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd">
  <flow name="updateSomething">
  </flow>
  <http:request />
</mule>
''')

    expectation.setCamelCased()

    expectation.handleNode(new MuleXmlNode(root.children()[0], nodeChecker))

    assert false == expectation.isPassing()
    assert 'Flow updateSomething is not camelCased' == expectation.infractions[0].message
  }
}
