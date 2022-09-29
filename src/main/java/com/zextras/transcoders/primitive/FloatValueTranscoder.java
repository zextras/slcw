package com.zextras.transcoders.primitive;

public class FloatValueTranscoder extends AbstractPrimitiveValueTranscoder<Float> {

  public FloatValueTranscoder() {
  }

  public FloatValueTranscoder(boolean b) {
    this.setPrimitive(b);
  }

  public Float decodeStringValue(String value) {
    return Float.valueOf(value);
  }

  public Class<Float> getType() {
    return this.isPrimitive() ? Float.TYPE : Float.class;
  }
}
