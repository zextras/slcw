package com.zextras.transcoders.primitive;

/**
 * Helper class for transcoding a string to a long or long wrapper object.
 */
public class LongValueTranscoder extends AbstractPrimitiveValueTranscoder<Long> {

  public LongValueTranscoder() {
  }

  public LongValueTranscoder(final boolean b) {
    this.setPrimitive(b);
  }

  /**
   * Decodes a string to a long.
   *
   * @param value string that you want to get a long value from.
   * @return a long object - the result of transcoding operation.
   */
  public Long decodeStringValue(final String value) {
    return Long.valueOf(value);
  }

  /**
   * Gives information about the class type a transcoder set with. This transcoder is used for Long
   * class.
   *
   * @return class information.
   */
  public Class<Long> getType() {
    return this.isPrimitive() ? Long.TYPE : Long.class;
  }
}
