package com.zextras.transcoders.primitive;

public class DoubleValueTranscoder extends AbstractPrimitiveValueTranscoder<Double> {
    public DoubleValueTranscoder() {
    }

    public DoubleValueTranscoder(boolean b) {
        this.setPrimitive(b);
    }

    public Double decodeStringValue(String value) {
        return Double.valueOf(value);
    }

    public Class<Double> getType() {
        return this.isPrimitive() ? Double.TYPE : Double.class;
    }
}
