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
    Reader reader = new File('src/test/resources/rules.txt').newReader()

    def version = new RulesLoader().findVersionNumber(reader)

    assert version == '0.0.1'
  }

  @Test
  void findVersionNumberReturnsFirstLineWhenInvalid() {
    Reader reader = new File('src/test/resources/rules-missing-version.txt').newReader()

    def version = new RulesLoader().findVersionNumber(reader)

    assert version == 'flows are camel cased'
  }
}
