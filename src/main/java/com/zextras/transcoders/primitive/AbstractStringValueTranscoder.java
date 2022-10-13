package com.zextras.transcoders.primitive;

import com.zextras.utils.TranscoderUtils;

/**
 * Helper class for decoding binary data to a string.
 */
public abstract class AbstractStringValueTranscoder<T> implements ValueTranscoder<T> {

  protected AbstractStringValueTranscoder() {
  }

  /**
   * Decodes a byte array to a string.
   *
   * @param value byte array that you want to get a string value from.
   * @return a string - the result of transcoding operation.
   */
  public T decodeBinaryValue(final byte[] value) {
    return this.decodeStringValue(TranscoderUtils.utf8Encode(value));
  }
}
