package com.zextras.transcoders.primitive;

import com.zextras.utils.TranscoderUtils;

public abstract class AbstractBinaryValueTranscoder<T> implements ValueTranscoder<T> {

  public AbstractBinaryValueTranscoder() {
  }

  public T decodeStringValue(String value) {
    return this.decodeBinaryValue(TranscoderUtils.utf8Encode(value));
  }
}
