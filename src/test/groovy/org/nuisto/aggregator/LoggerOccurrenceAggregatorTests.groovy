package org.nuisto.aggregator

import org.junit.Before
import org.junit.Test
import org.nuisto.MuleXmlNode
import org.nuisto.NodeChecker

class LoggerOccurrenceAggregatorTests {
  LoggerOccurrenceAggregator aggregator
  NodeChecker nodeChecker

  @Before
  void setup() {
    aggregator = new LoggerOccurrenceAggregator()
    nodeChecker = new NodeChecker(['http': 'http://www.mulesoft.org/schema/mule/http'])
  }

  @Test
  void muleFileHasNoLoggerReferences() {

    def root = new XmlParser().parseText('''
<mule xmlns:http="http://www.mulesoft.org/schema/mule/http"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd">
  <http:request />
</mule>
''')

    aggregator.handleNode(new MuleXmlNode(root.children()[0], nodeChecker))

    assert aggregator.totals.get('loggerCount') == 0
  }

  @Test
  void muleFileHasSingleLoggerReference() {

    def root = new XmlParser().parseText('''
<mule xmlns:http="http://www.mulesoft.org/schema/mule/http"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd">
  <logger />
</mule>
''')

    aggregator.handleNode(new MuleXmlNode(root.children()[0], nodeChecker))

    assert aggregator.totals.get('loggerCount') == 1
  }
}
