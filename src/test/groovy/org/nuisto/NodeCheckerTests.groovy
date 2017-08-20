package org.nuisto

import org.junit.Test

class MuleNodeTests {
  @Test
  void coreElementIsMatched() {

    def root = new XmlParser().parseText('<mule> <logger /> </mule>')

    assert NodeChecker.isMatch(root.children()[0], 'logger')
  }

  @Test
  void httpElementIsMatched() {

    def foo = this.class.getResource('src/test/resources/example-mule.xml')

    def xml = '''<?xml version="1.0" encoding="UTF-8"?><mule xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.mulesoft.org/schema/mule/core" xmlns:http="http://www.mulesoft.org/schema/mule/http" xsi:schemaLocation="http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd http://www.mulesoft.org/schema/mule/core http://www.mulesoft.org/schema/mule/core/current/mule.xsd"><sub-flow name="exampleFlow"><http:request config-ref="httpRequestConfiguration" path="${api.path}" method="POST"/><logger message="" level=""/></sub-flow></mule>'''

    def root = new XmlParser().parseText(xml)

    assert NodeChecker.isMatch(root.children()[0].children()[0], 'http:request')
  }

  @Test
  void unknownElementIsNotMatched() {

    def foo = this.class.getResource('src/test/resources/example-mule.xml')

    def xml = '''<?xml version="1.0" encoding="UTF-8"?><mule xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://www.mulesoft.org/schema/mule/core" xmlns:http="http://www.mulesoft.org/schema/mule/http" xsi:schemaLocation="http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd http://www.mulesoft.org/schema/mule/core http://www.mulesoft.org/schema/mule/core/current/mule.xsd"><sub-flow name="exampleFlow"><http:request config-ref="httpRequestConfiguration" path="${api.path}" method="POST"/></sub-flow></mule>'''

    def root = new XmlParser().parseText(xml)

    assert false == NodeChecker.isMatch(root.children()[0], 'this means nothing')
  }
}
