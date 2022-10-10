package com.zextras.transcoders.primitive;

/**
 * Helper class for transcoding a string to a float or float wrapper object.
 */
public class FloatValueTranscoder extends AbstractPrimitiveValueTranscoder<Float> {

  public FloatValueTranscoder() {
  }

  public FloatValueTranscoder(boolean b) {
    this.setPrimitive(b);
  }

  /**
   * Decodes a string to a float.
   *
   * @param value string that you want to get a float value from.
   * @return a float object - the result of transcoding operation.
   */
  public Float decodeStringValue(String value) {
    return Float.valueOf(value);
  }

  /**
   * Gives information about the class type a transcoder set with. This transcoder is used for Float
   * class.
   *
   * @return class information.
   */
  public Class<Float> getType() {
    return this.isPrimitive() ? Float.TYPE : Float.class;
  }
}
