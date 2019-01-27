package org.nuisto.aggregator

import org.nuisto.MuleXmlNode

interface Aggregator {
  void reset()

  void handleNode(MuleXmlNode node)

  Map<String, Integer> getTotals()
}