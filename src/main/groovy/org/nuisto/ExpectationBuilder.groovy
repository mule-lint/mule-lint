package org.nuisto

import groovy.util.logging.Slf4j

@Slf4j(category = 'org.nuisto.msa')
abstract class ExpectationBuilder {
  ElementExpectation currentExpectation

  ExpectationBuilder() {
  }

  public Expectation build() {

  }

  /**
   * This is in essence the "build" method in the builder pattern.
   * I felt like the build didn't make sense in this case.
   * We could have had it, but instead of having to call "build", then
   * another method call to add the built expectation to a list and problems
   * around handling nulls, I felt like having this instead.
   * @param expectationList
   */
  public void addBuiltExpectationTo(List<Expectation> expectationList) {

  }
}
