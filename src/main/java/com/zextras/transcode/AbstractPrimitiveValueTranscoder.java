package com.zextras.transcode;

public abstract class AbstractPrimitiveValueTranscoder<T> extends AbstractStringValueTranscoder<T> {
    private boolean primitive;

    public AbstractPrimitiveValueTranscoder() {
    }

    public boolean isPrimitive() {
        return this.primitive;
    }

    public void setPrimitive(boolean b) {
        this.primitive = b;
    }

    public String encodeStringValue(T value) {
        return value.toString();
    }
}
