package com.zextras.transcode.primitive;

public interface ValueTranscoder<T> {
    T decodeStringValue(String var1);

    T decodeBinaryValue(byte[] var1);

    Class<T> getType();
}
