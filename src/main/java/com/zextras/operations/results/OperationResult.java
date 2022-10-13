package com.zextras.operations.results;

/**
 * Class that provides information about the result of operations you perform.
 */
public abstract class OperationResult {

  private final String name;
  private final int intValue;

  /**
   * Creates an operation result with code value and message information.
   *
   * @param name     plain message of an operation which is clear to you.
   * @param intValue plain operation code which is clear to you.
   */
  protected OperationResult(final String name, final int intValue) {
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
