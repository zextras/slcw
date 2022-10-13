package com.zextras.transcoders.primitive;

/**
 * Helper class for transcoding a string to an int or integer wrapper object.
 */
public class IntegerValueTranscoder extends AbstractPrimitiveValueTranscoder<Integer> {

  public IntegerValueTranscoder() {
  }

  public IntegerValueTranscoder(final boolean b) {
    this.setPrimitive(b);
  }

  /**
   * Decodes a string to an integer.
   *
   * @param value string that you want to get an integer value from.
   * @return an integer object - the result of transcoding operation.
   */
  public Integer decodeStringValue(final String value) {
    return Integer.valueOf(value);
  }

  /**
   * Gives information about the class type a transcoder set with. This transcoder is used for
   * Integer class.
   *
   * @return class information.
   */
  public Class<Integer> getType() {
    return this.isPrimitive() ? Integer.TYPE : Integer.class;
  }
}
