package org.nuisto

class FlowRule {
  ExpectationBuilder builder

  FlowRule(ExpectationBuilder builder) {
    this.builder = builder
  }

  FlowRule flows(String ignore) {
    //The ignore parameter is "are"
    return this
  }

  FlowRule camel(String ignore) {
    //The "ignore" parameter is "cased"
    return this
  }
}
