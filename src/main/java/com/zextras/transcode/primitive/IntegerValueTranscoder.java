package com.zextras.transcode.primitive;

public class IntegerValueTranscoder extends AbstractPrimitiveValueTranscoder<Integer> {
    public IntegerValueTranscoder() {
    }

    public IntegerValueTranscoder(boolean b) {
        this.setPrimitive(b);
    }

    public Integer decodeStringValue(String value) {
        return Integer.valueOf(value);
    }

    public Class<Integer> getType() {
        return this.isPrimitive() ? Integer.TYPE : Integer.class;
    }
}
