package org.nuisto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.nuisto.model.OptionsModel;

import static org.junit.jupiter.api.Assertions.*;

class FlowExpectationBuilderTest {

  FlowExpectationBuilder builder

  @BeforeEach
  void setUp() {
    def model = new OptionsModel(dictionary: 'src/test/resources/dictionary.txt')
    builder = new FlowExpectationBuilder(model)
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void dash() {
    def result = builder.dash('a string that should be ignored')

    assert result != null
  }

  @Test
  void hypen() {
    def result = builder.hypen('a string that should be ignored')

    assert result != null
  }

  @Test
  void camel() {
    def result = builder.camel('a string that should be ignored')

    assert result != null
  }

  @Test
  void pascal() {
    def result = builder.pascal('a string that should be ignored')

    assert result != null
  }
}
