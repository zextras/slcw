package com.zextras.operations.results;

import com.zextras.SlcwBean;
import java.util.List;

/**
 * Class that provides information about the result of operations you perform.
 */
public class OperationResult<T extends SlcwBean> {

  private final String name;
  private final int intValue;

  public List<T> getData() {
    return data;
  }

  private final List<T> data;

  /**
   * Creates an operation result with code value and message information.
   *  @param name     plain message of an operation which is clear to you.
   * @param intValue plain operation code which is clear to you.
   * @param data data returned from result if any
   */
  public OperationResult(String name, int intValue, List<T> data) {
    this.name = name;
    this.intValue = intValue;
    this.data = data;
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
