package com.zextras.transcoders.primitive;

public class BooleanValueTranscoder extends AbstractPrimitiveValueTranscoder<Boolean> {
    public BooleanValueTranscoder() {
    }

    public BooleanValueTranscoder(boolean b) {
        this.setPrimitive(b);
    }

    public Boolean decodeStringValue(String value) {
        return Boolean.valueOf(value);
    }

    public Class<Boolean> getType() {
        return this.isPrimitive() ? Boolean.TYPE : Boolean.class;
    }
}
