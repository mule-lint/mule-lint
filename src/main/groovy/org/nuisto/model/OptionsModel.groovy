package org.nuisto.model

class OptionsModel {
  String rules
  String sourceDirectory
  String resultsFile

  Map<String, String> namespaces = new HashMap<>()
}
