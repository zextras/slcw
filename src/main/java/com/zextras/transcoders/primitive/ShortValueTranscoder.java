package com.zextras.transcoders.primitive;

/**
 * Helper class for transcoding a string to a short or short wrapper object.
 */
public class ShortValueTranscoder extends AbstractPrimitiveValueTranscoder<Short> {

  public ShortValueTranscoder() {
  }

  public ShortValueTranscoder(final boolean b) {
    this.setPrimitive(b);
  }

  /**
   * Decodes a string to a short.
   *
   * @param value string that you want to get a short value from.
   * @return a short object - the result of transcoding operation.
   */
  public Short decodeStringValue(final String value) {
    return Short.valueOf(value);
  }

  /**
   * Gives information about the class type a transcoder set with. This transcoder is used for Short
   * class.
   *
   * @return class information.
   */
  public Class<Short> getType() {
    return this.isPrimitive() ? Short.TYPE : Short.class;
  }
}
