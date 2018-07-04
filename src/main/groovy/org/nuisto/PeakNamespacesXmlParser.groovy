package org.nuisto

import groovy.util.logging.Slf4j
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.ext.Attributes2Impl

@Slf4j(category = 'org.nuisto.msa')
class PeakNamespacesXmlParser extends XmlParser {
  public static final String LINE_NUM_ATTR = '_msaLineNumber'
  public static final String COL_NUM_ATTR = '_msaColNumber'

  Map<String, String> foundNamespaces = null
  String filename

  PeakNamespacesXmlParser(Map<String, String> foundNamespaces) {
    this.foundNamespaces = foundNamespaces
  }

  Node parse(String uri) throws IOException, SAXException {
    filename = uri
    super.parse(uri)
  }

  void startPrefixMapping(final String tag, final String uri) throws SAXException {
    super.startPrefixMapping(tag, uri)

    foundNamespaces.put(tag, uri)
  }

  void startElement(String namespaceURI, String localName, String qName, Attributes attributes) {
    Attributes2Impl newAttrs = new Attributes2Impl(attributes)
    newAttrs.addAttribute('', LINE_NUM_ATTR, LINE_NUM_ATTR, 'ENTITY', '' + getDocumentLocator().lineNumber)
    newAttrs.addAttribute('', COL_NUM_ATTR, COL_NUM_ATTR, 'ENTITY', '' + getDocumentLocator().columnNumber)

    super.startElement(namespaceURI, localName, qName, newAttrs)
  }
}
