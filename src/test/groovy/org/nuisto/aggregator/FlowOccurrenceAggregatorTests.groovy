package org.nuisto.aggregator

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.nuisto.MuleXmlNode
import org.nuisto.NodeChecker

class FlowOccurrenceAggregatorTests {
  FlowOccurrenceAggregator aggregator
  NodeChecker nodeChecker

  @BeforeEach
  void setup() {
    aggregator = new FlowOccurrenceAggregator()
    nodeChecker = new NodeChecker(['http': 'http://www.mulesoft.org/schema/mule/http'])
  }

  @Test
  void muleFileHasNoFlowReferences() {

    def root = new XmlParser().parseText('''
<mule xmlns:http="http://www.mulesoft.org/schema/mule/http"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd">
  <http:request />
  <logger />
</mule>
''')

    aggregator.handleNode(new MuleXmlNode(root.children()[0], nodeChecker))

    assert aggregator.totals.get('flowCount') == 0
  }

  @Test
  void muleFileHasSingleFlowReference() {

    def root = new XmlParser().parseText('''
<mule xmlns:http="http://www.mulesoft.org/schema/mule/http"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd">
  <flow />
</mule>
''')

    aggregator.handleNode(new MuleXmlNode(root.children()[0], nodeChecker))

    assert aggregator.totals.get('flowCount') == 1
  }

  @Test
  void muleFileHasSubFlowReferences() {

    def root = new XmlParser().parseText('''
<mule xmlns:http="http://www.mulesoft.org/schema/mule/http"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd">
  <flow />
  <sub-flow />
</mule>
''')

    aggregator.handleNode(new MuleXmlNode(root.children()[0], nodeChecker))
    aggregator.handleNode(new MuleXmlNode(root.children()[1], nodeChecker))

    assert aggregator.totals.get('flowCount') == 2
  }
}
