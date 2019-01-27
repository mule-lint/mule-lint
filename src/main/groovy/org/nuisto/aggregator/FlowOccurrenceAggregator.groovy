package org.nuisto.aggregator

import org.nuisto.MuleXmlNode

class FlowOccurrenceAggregator implements Aggregator {
  int flowCount

  FlowOccurrenceAggregator() {
    init()
  }

  void init() {
    flowCount = 0
  }

  void reset() {
    init()
  }

  void handleNode(MuleXmlNode node) {
    if (node.isMatch('flow')) {
      ++flowCount
    }
    if (node.isMatch('sub-flow')) {
      ++flowCount
    }
  }

  Map<String, Integer> getTotals() {
    return ['flowCount': flowCount]
  }
}
