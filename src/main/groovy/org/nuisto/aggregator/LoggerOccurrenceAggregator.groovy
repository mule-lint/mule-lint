package org.nuisto.aggregator

import org.nuisto.MuleXmlNode

class LoggerOccurrenceAggregator implements Aggregator {

  int loggerCount

  LoggerOccurrenceAggregator() {
    init()
  }

  void init() {
    loggerCount = 0
  }

  void reset() {
    init()
  }

  void handleNode(MuleXmlNode node) {
    if (node.isMatch('logger')) {
      ++loggerCount
    }
  }

  Map<String, Integer> getTotals() {
    return ['loggerCount': loggerCount]
  }
}
