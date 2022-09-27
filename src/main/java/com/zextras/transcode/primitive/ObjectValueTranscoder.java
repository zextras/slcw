package com.zextras.transcode.primitive;

public class ObjectValueTranscoder implements ValueTranscoder<Object> {
    public ObjectValueTranscoder() {
    }

    public Object decodeStringValue(String value) {
        return value;
    }

    public Object decodeBinaryValue(byte[] value) {
        return value;
    }

    public Class<Object> getType() {
        return Object.class;
    }
}
