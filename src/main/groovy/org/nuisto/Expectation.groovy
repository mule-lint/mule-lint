package org.nuisto

import org.nuisto.model.Infraction

abstract class Expectation {

  boolean isPassing

  List<Infraction> infractions

  void init() {

  }

  /**
   * TODO I don't like this method name. It isn't descriptive to what is happening
   * We have to have some notion of "resetting" based upon a new file.
   *
   * An expectation is based on a file, when we have a new file, then the expectation should be reset.
   *
   * Might turn this into more like event based "onNewFile", but I'm not sure about that.
   */
  void reset() {

  }

  boolean isPassing() {

  }

  void handleNode(MuleXmlNode node) {

  }
}
