package com.zextras.transcode.primitive;

import com.zextras.transcode.TranscoderUtils;

public abstract class AbstractStringValueTranscoder<T> implements ValueTranscoder<T> {
    public AbstractStringValueTranscoder() {
    }

    public T decodeBinaryValue(byte[] value) {
        return this.decodeStringValue(TranscoderUtils.utf8Encode(value));
    }
}
