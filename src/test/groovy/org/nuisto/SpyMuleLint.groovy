package org.nuisto

import org.nuisto.model.OptionsModel

class SpyMuleLint extends MuleLint {
  OptionsModel modelUsed

  void runWithModel(OptionsModel model) {
    this.modelUsed = model
  }
}
