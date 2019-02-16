package org.nuisto

import org.nuisto.model.OptionsModel

class SpyMuleLint extends MuleLint {
  OptionsModel modelUsed

  int runWithModel(OptionsModel model) {
    this.modelUsed = model
    return ErrorCodes.Success
  }
}
