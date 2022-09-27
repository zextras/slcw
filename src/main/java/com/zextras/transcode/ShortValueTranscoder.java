package com.zextras.transcode;

public class ShortValueTranscoder extends AbstractPrimitiveValueTranscoder<Short> {
    public ShortValueTranscoder() {
    }

    public ShortValueTranscoder(boolean b) {
        this.setPrimitive(b);
    }

    public Short decodeStringValue(String value) {
        return Short.valueOf(value);
    }

    public Class<Short> getType() {
        return this.isPrimitive() ? Short.TYPE : Short.class;
    }
}
