package org.nuisto

import org.junit.jupiter.api.Test

class MuleXmlParserTests {
  @Test
  public void isMuleFileReturnsTrue() {

    MuleXmlParser parser = new MuleXmlParser('src/test/resources/example-mule.xml')

    assert parser.isMuleFile()
  }
}
