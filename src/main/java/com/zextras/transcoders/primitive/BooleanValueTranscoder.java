package com.zextras.transcoders.primitive;

/**
 * Helper class for transcoding a string to a boolean or boolean wrapper object.
 */
public class BooleanValueTranscoder extends AbstractPrimitiveValueTranscoder<Boolean> {

  public BooleanValueTranscoder() {
  }

  public BooleanValueTranscoder(final boolean b) {
    this.setPrimitive(b);
  }

  /**
   * Decodes a string to a boolean.
   *
   * @param value string that you want to get a boolean value from.
   * @return a double object - the result of transcoding operation.
   */
  public Boolean decodeStringValue(final String value) {
    return Boolean.valueOf(value);
  }

  /**
   * Gives information about the class type a transcoder set with. This transcoder is used for
   * Boolean class.
   *
   * @return class information.
   */
  public Class<Boolean> getType() {
    return this.isPrimitive() ? Boolean.TYPE : Boolean.class;
  }
}
