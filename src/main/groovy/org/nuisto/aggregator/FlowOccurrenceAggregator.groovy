package org.nuisto.aggregator

import org.nuisto.NodeChecker

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

  void handleNode(Node node, NodeChecker nodeChecker) {
    if (nodeChecker.isMatch(node, 'flow')) {
      ++flowCount
    }
    if (nodeChecker.isMatch(node, 'sub-flow')) {
      ++flowCount
    }
  }

  Map<String, Integer> getTotals() {
    return ['flowCount': flowCount]
  }
}
