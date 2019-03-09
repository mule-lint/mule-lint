package org.nuisto

abstract class VersionBaseScriptClass extends Script {
  void version(String version) {
    this.binding.version = version
  }
}
