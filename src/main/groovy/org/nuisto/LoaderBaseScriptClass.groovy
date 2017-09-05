package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j(category = 'org.nuisto.msa')
abstract class LoaderBaseScriptClass extends Script {
  ElementRule version(String version) {
    this.binding.elementRule.version(version)

    this.binding.elementRule = new ElementRule(this.binding.elementRule.builder)

    return this.binding.elementRule
  }

  ElementRule element(String name) {
    return this.binding.elementRule.element(name)
  }
}


