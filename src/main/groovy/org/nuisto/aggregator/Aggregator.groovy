package org.nuisto.aggregator

import org.nuisto.NodeChecker

interface Aggregator {
  void reset()

  void handleNode(Node node, NodeChecker nodeChecker)

  Map<String, Integer> getTotals()
}