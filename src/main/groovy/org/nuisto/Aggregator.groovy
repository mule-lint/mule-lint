package org.nuisto

interface Aggregator {
  void reset()

  void handleNode(Node node, NodeChecker nodeChecker)

  Map<String, Integer> getTotals()
}