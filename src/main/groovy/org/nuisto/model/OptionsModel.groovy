package org.nuisto.model

class OptionsModel {
  boolean failBuild
  String rules
  String sourceDirectory
  String resultsFile
  String dictionary
  String [] excludePatterns

  Map<String, String> namespaces = new HashMap<>()
}
