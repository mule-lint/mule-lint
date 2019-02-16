package org.nuisto.model

class OptionsModel {
  String rules
  String sourceDirectory
  String resultsFile
  String dictionary
  String [] excludePatterns

  Map<String, String> namespaces = new HashMap<>()
}
