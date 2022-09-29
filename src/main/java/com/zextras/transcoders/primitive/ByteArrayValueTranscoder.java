package com.zextras.transcoders.primitive;

/**
 * Helper class for transcoding byte arrays.
 */
public class ByteArrayValueTranscoder extends AbstractBinaryValueTranscoder<byte[]> {

  public ByteArrayValueTranscoder() {
  }

  /**
   * Returns a given value as no need to transcode.
   *
   * @param value byte array.
   * @return a given value.
   */
  public byte[] decodeBinaryValue(byte[] value) {
    return value;
  }

  /**
   * Gives information about the class type a transcoder set with. This transcoder is used for byte
   * array class.
   *
   * @return class information.
   */
  public Class<byte[]> getType() {
    return byte[].class;
  }
}
