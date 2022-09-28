package com.zextras.transcoders.primitive;

public class StringValueTranscoder extends AbstractStringValueTranscoder<String> {
    public StringValueTranscoder() {
    }

    public String decodeStringValue(String value) {
        return value;
    }

    public Class<String> getType() {
        return String.class;
    }
}
