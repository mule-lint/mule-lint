package org.nuisto

import org.junit.jupiter.api.Test

class MuleXmlNodeTests {

  NodeChecker nodeChecker = new NodeChecker(['http': 'http://www.mulesoft.org/schema/mule/http'])

  @Test
  public void flowesqueReturnsTrueForFlow() {
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
    MuleXmlNode node = new MuleXmlNode(root.children()[0], nodeChecker)

    assert node.flowesque
  }

  @Test
  public void flowesqueReturnsTrueForSubFlow() {
    def root = new XmlParser().parseText('''
<mule xmlns:http="http://www.mulesoft.org/schema/mule/http"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
http://www.mulesoft.org/schema/mule/http http://www.mulesoft.org/schema/mule/http/current/mule-http.xsd">
  <sub-flow name="updateSomething">
  </sub-flow>
  <http:request />
</mule>
''')
    MuleXmlNode node = new MuleXmlNode(root.children()[0], nodeChecker)

    assert node.flowesque
  }
}
