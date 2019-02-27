package org.nuisto

import org.nuisto.model.OptionsModel

class FlowExpectationBuilder extends ExpectationBuilder {
  FlowExpectation expectation
  OptionsModel optionsModel

  FlowExpectationBuilder(OptionsModel optionsModel) {
    this.optionsModel = optionsModel

    List<String> knownWords = new File(optionsModel.dictionary).readLines()

    //TODO Thinking we take a factory to create the FlowExpectations
    expectation = new FlowExpectation(new WordValidator(knownWords))
  }

  public Expectation build() {

  }

  public void addBuiltExpectationTo(List<Expectation> expectationList) {
    expectationList.add(expectation)
  }

  FlowExpectationBuilder dash(String ignore) {
    //The "ignore" parameter is "cased"
    expectation.setDashCased()
    return this
  }

  FlowExpectationBuilder hypen(String ignore) {
    //The "ignore" parameter is "cased"
    expectation.setDashCased()
    return this
  }

  FlowExpectationBuilder camel(String ignore) {
    //The "ignore" parameter is "cased"
    expectation.setCamelCased()
    return this
  }

  FlowExpectationBuilder pascal(String ignore) {
    //The "ignore" parameter is "cased"
    expectation.setPascalCased()
    return this
  }
}
