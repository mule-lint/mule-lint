package org.nuisto.v001a

import groovy.util.logging.Slf4j
import org.nuisto.ExpectationBuilder

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

  /*
  def methodMissing(String methodName, args) {
    throw new Exception("m${methodName} is not a known rule word for mule-lint")
  }

  def propertyMissing(String name) {
    throw new Exception("p${name} is not a known rule word for mule-lint")
  }
  */

  void flows() {
    // TODO
  }

  void element(String name, Closure closure) {
    def builderDelegate = new ElementExpectationBuilderDelegate(name, this.binding.optionsModel)
    closure.delegate = builderDelegate
    closure.call()

    addToBinding(this.binding.builders, builderDelegate)

    // We need to get the expectations out of the above BuilderDelegate and add to what?
    // Add each one to binding.builders - that is what we use to iterator through and get the expectations
  }

  void addToBinding(List<ExpectationBuilder> list, ExpectationBuilder builder) {
    list.add(builder)
  }
}
