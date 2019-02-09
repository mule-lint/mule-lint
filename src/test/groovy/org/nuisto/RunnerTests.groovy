package org.nuisto

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.nuisto.model.OptionsModel

class RunnerTests {
  Runner runner

  @BeforeEach
  public void setup() {
    runner = new Runner(new DummyResultsHandler())
  }

  @Disabled
  @Test
  void infractionsAreTalliedUpCorrectlyAndNotDuplicated() {
    OptionsModel model = new OptionsModel( sourceDirectory: 'src/test/resources/samples', rules: 'src/test/resources/samples/rules.txt')

    int result = runner.runWithModel(model)

    //I have found infractions where they keep getting outputted repeatedly.
    //Write a test and get this to fail, then fix it
    //Update: This doesn't seem to be the case anymore. But I'll leave this test here as something to build upon.
    //Do need to create tests around the runner
    assert 0 == result
  }


  @Test
  public void processFile___Foo() {
    runner.processFile('src/test/resources/example-mule.xml', [], [])
  }
}
