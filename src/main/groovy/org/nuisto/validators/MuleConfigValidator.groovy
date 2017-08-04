package org.nuisto.validators

abstract class MuleConfigValidator {
  boolean success

  String message

  abstract boolean validate(Node node)
}
