package org.nuisto

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RulesLoaderTest {

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void findVersionNumber() {
    def version = new RulesLoader().findVersionNumber("version '0.0.1'")

    assert version == '0.0.1'
  }

  @Test
  void findVersionNumberReturnsFirstLineWhenInvalid() {
    String badVersionLine = 'flows are camel cased'
    def version = new RulesLoader().findVersionNumber(badVersionLine)

    assert version == badVersionLine
  }
}
