package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j(category = 'org.nuisto.msa')
abstract class LoaderBaseScriptClass extends Script {
  String are = 'are'
  String cased = 'cased'

  ElementRule version(String version) {
    this.binding.elementRule.version(version)

    this.binding.elementRule = new ElementRule(this.binding.elementRule.builder)

    return this.binding.elementRule
  }

  FlowRule flows(String ignore) {
    //Ignored parameter is the value of the 'are' member

    return this.binding.flowRule
  }

  ElementRule element(String name) {
    return this.binding.elementRule.element(name)
  }
}


