package org.nuisto

import org.junit.Ignore
import org.junit.Test
import org.nuisto.model.OptionsModel

class RunnerTests {
  @Ignore
  @Test
  void infractionsAreTalliedUpCorrectlyAndNotDuplicated() {
    Runner runner = new Runner(new DummyResultsHandler())

    OptionsModel model = new OptionsModel( sourceDirectory: 'src/test/resources/samples', rules: 'src/test/resources/samples/rules.txt')

    int result = runner.runWithModel(model)

    //I have found infractions where they keep getting outputted repeatedly.
    //Write a test and get this to fail, then fix it
    //Update: This doesn't seem to be the case anymore. But I'll leave this test here as something to build upon.
    //Do need to create tests around the runner
    assert 0 == result
  }
}
