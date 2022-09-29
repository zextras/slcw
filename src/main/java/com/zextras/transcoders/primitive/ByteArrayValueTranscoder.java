package com.zextras.transcoders.primitive;

public class ByteArrayValueTranscoder extends AbstractBinaryValueTranscoder<byte[]> {

  public ByteArrayValueTranscoder() {
  }

  public byte[] decodeBinaryValue(byte[] value) {
    return value;
  }

  public Class<byte[]> getType() {
    return byte[].class;
  }
}
