package com.zextras.transcoders.primitive;

import com.zextras.utils.TranscoderUtils;

public abstract class AbstractStringValueTranscoder<T> implements ValueTranscoder<T> {

  public AbstractStringValueTranscoder() {
  }

  public T decodeBinaryValue(byte[] value) {
    return this.decodeStringValue(TranscoderUtils.utf8Encode(value));
  }
}
