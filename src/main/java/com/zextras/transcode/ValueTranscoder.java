package com.zextras.transcode;

import java.util.function.Function;

public interface ValueTranscoder<T> {
    T decodeStringValue(String var1);

    T decodeBinaryValue(byte[] var1);

    Class<T> getType();

    default Function<byte[], T> decoder() {
        return this::decodeBinaryValue;
    }
}
