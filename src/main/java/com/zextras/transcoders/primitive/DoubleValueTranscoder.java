package com.zextras.transcoders.primitive;

/**
 * Helper class for transcoding a string to a double or double wrapper object.
 */
public class DoubleValueTranscoder extends AbstractPrimitiveValueTranscoder<Double> {

  public DoubleValueTranscoder() {
  }

  public DoubleValueTranscoder(boolean b) {
    this.setPrimitive(b);
  }

  /**
   * Decodes a string to a double.
   *
   * @param value string that you want to get a double value from.
   * @return a double object - the result of transcoding operation.
   */
  public Double decodeStringValue(String value) {
    return Double.valueOf(value);
  }

  /**
   * Gives information about the class type a transcoder set with. This transcoder is used for
   * Double class.
   *
   * @return class information.
   */
  public Class<Double> getType() {
    return this.isPrimitive() ? Double.TYPE : Double.class;
  }
}
