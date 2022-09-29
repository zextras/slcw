package com.zextras.transcoders.primitive;

public class CharArrayValueTranscoder extends AbstractStringValueTranscoder<char[]> {

  public CharArrayValueTranscoder() {
  }

  public char[] decodeStringValue(String value) {
    return value.toCharArray();
  }

  public String encodeStringValue(char[] value) {
    return String.valueOf(value);
  }

  public Class<char[]> getType() {
    return char[].class;
  }
}
