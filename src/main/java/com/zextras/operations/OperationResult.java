package com.zextras.operations;

/**
 * Class that provides information about the result of operations you perform.
 */
public class OperationResult {

  private final String name;
  private final int intValue;
  private final String stringRepresentation;

  /**
   * @param name     plain message of an operation which is clear to you.
   * @param intValue plain operation code which is clear to you.
   */
  public OperationResult(String name, int intValue) {
    this.name = name;
    this.intValue = intValue;
    this.stringRepresentation = intValue + " (" + name + ')';
  }

  public String getName() {
    return name;
  }

  public int getIntValue() {
    return intValue;
  }

  public String getStringRepresentation() {
    return stringRepresentation;
  }
}
