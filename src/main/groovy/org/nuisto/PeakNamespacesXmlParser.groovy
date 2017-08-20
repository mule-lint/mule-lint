package org.nuisto

import groovy.util.logging.Slf4j
import org.xml.sax.SAXException

@Slf4j(category = 'org.nuisto.msa')
class PeakNamespacesXmlParser extends XmlParser {
  Map<String, String> foundNamespaces = null

  PeakNamespacesXmlParser(Map<String, String> foundNamespaces) {
    this.foundNamespaces = foundNamespaces
  }

  void startPrefixMapping(final String tag, final String uri) throws SAXException {
    super.startPrefixMapping(tag, uri)

    foundNamespaces.put(tag, uri)
  }
}
