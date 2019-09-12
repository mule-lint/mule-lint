package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j(category = 'org.nuisto.msa')
abstract class ExpectationBuilder {

  ExpectationBuilder() {
  }

  /**
   * This is the "build" method in the builder pattern.
   */
  public Expectation build() {

  }
}
