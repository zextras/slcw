package com.zextras.transcoders.primitive;

import com.zextras.utils.TranscoderUtils;

/**
 * Helper class for decoding a string to binary data.
 */
public abstract class AbstractBinaryValueTranscoder<T> implements ValueTranscoder<T> {

  protected AbstractBinaryValueTranscoder() {
  }

  /**
   * Decodes a string to a byte array.
   *
   * @param value string that you want to get a byte array  value from.
   * @return a byte array - the result of transcoding operation.
   */
  public T decodeStringValue(final String value) {
    return this.decodeBinaryValue(TranscoderUtils.utf8Encode(value));
  }
}
