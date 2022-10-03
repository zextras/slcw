package com.zextras.operations;

/**
 * Class that provides information about the result of operations you perform.
 */
public class OperationResult {

  private final String name;
  private final int intValue;

  /**
   * @param name     plain message of an operation which is clear to you.
   * @param intValue plain operation code which is clear to you.
   */
  public OperationResult(String name, int intValue) {
    this.name = name;
    this.intValue = intValue;
  }

  public String getName() {
    return name;
  }

  public int getIntValue() {
    return intValue;
  }

  @Override
  public String toString() {
    return intValue + " (" + name + ')';
  }
}
