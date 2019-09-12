package org.nuisto

import groovy.util.logging.Slf4j

/**
 * Needs to follow more closely to the builder pattern
 *
 * When a method is first called, expect that is a new
 * line in the rules and a new expectation.
 *
 * Second thought - maybe it just creates a builder and
 * hands off each method call to a "flow builder" or
 * "element builder". The builder would need to be
 * based on an interface so each method invocation
 * we can get the constructed object and add it to
 * a collection
 */
@Slf4j(category = 'org.nuisto.msa')
abstract class LoaderBaseScriptClass extends Script {
  String are = 'are'
  String cased = 'cased'

  ExpectationBuilder builder = new NullExpectationBuilder()

  def methodMissing(String methodName, args) {
    throw new Exception("${methodName} is not a known rule word for mule-lint")
  }

  def propertyMissing(String name) {
    throw new Exception("${name} is not a known rule word for mule-lint")
  }

  FlowExpectationBuilder flows(String ignore) {
    //Ignored parameter is the value of the 'are' member

    FlowExpectationBuilder builder = FlowExpectationBuilder.create(this.binding.optionsModel)
    addToBinding(this.binding.builders, builder)
    return builder
  }

  ElementExpectationBuilder element(String name) {
    ElementExpectationBuilder builder = ElementExpectationBuilder.create(name, this.binding.optionsModel)
    addToBinding(this.binding.builders, builder)
    return builder
  }

  void addToBinding(List<ExpectationBuilder> list, ExpectationBuilder builder) {
    list.add(builder)
  }
}
