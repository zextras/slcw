package com.zextras.transcoders.reflection;

import com.zextras.transcoders.primitive.ValueTranscoder;

/**
 * Helper collective class that provides basically primitive and primitive wrapper transcoders which
 * are used by ReflectionUtils class in order to set values.
 */
public class SingleValueReflectionTranscoder<T> implements ReflectionTranscoder {

  private final ValueTranscoder<T> valueTranscoder;

  /**
   * Creates a SingleValueReflectionTranscoder of a corresponding type.
   *
   * @param transcoder a transcoder with corresponding value that you wanted to use.
   */
  public SingleValueReflectionTranscoder(ValueTranscoder<T> transcoder) {
    this.valueTranscoder = transcoder;
  }

  /**
   * Decodes string value to the needed object by using a corresponding transcoder.
   *
   * @param value string that you want to get a value from.
   * @return corresponding object of transcoding operation.
   */
  public Object decodeStringValue(String value) {
    return this.valueTranscoder.decodeStringValue(value);
  }

  /**
   * Gives information about the type of class a transcoder set with.
   *
   * @return class information.
   */
  public Class<?> getType() {
    return this.valueTranscoder.getType();
  }

  /**
   * Gives information if the current transcoder supports a given type of class.
   *
   * @param type type of class that you want to check.
   * @return true if transcoder supports given type, otherwise false.
   */
  public boolean supports(Class<?> type) {
    return this.getType().equals(type);
  }
}
