package com.zextras.transcode.primitive;

public class LongValueTranscoder extends AbstractPrimitiveValueTranscoder<Long> {
    public LongValueTranscoder() {
    }

    public LongValueTranscoder(boolean b) {
        this.setPrimitive(b);
    }

    public Long decodeStringValue(String value) {
        return Long.valueOf(value);
    }

    public Class<Long> getType() {
        return this.isPrimitive() ? Long.TYPE : Long.class;
    }
}
