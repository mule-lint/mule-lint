package org.nuisto

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

  void handleNode(Node node, NodeChecker nodeChecker) {
    if (nodeChecker.isMatch(node, 'logger')) {
      ++loggerCount
    }
  }

  Map<String, Integer> getTotals() {
    return ['loggerCount': loggerCount]
  }
}
