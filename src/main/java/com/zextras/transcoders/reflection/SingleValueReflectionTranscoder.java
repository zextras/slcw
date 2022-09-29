package com.zextras.transcoders.reflection;

import com.zextras.transcoders.primitive.ValueTranscoder;

public class SingleValueReflectionTranscoder<T> implements ReflectionTranscoder {

  private final ValueTranscoder<T> valueTranscoder;

  public SingleValueReflectionTranscoder(ValueTranscoder<T> transcoder) {
    this.valueTranscoder = transcoder;
  }

  public static <T> SingleValueReflectionTranscoder<T> newInstance(ValueTranscoder<T> transcoder) {
    return new SingleValueReflectionTranscoder(transcoder);
  }

  public Object decodeStringValue(String value) {
    return this.valueTranscoder.decodeStringValue(value);
  }

  public Class<?> getType() {
    return this.valueTranscoder.getType();
  }

  public boolean supports(Class<?> type) {
    return this.getType().equals(type);
  }
}
