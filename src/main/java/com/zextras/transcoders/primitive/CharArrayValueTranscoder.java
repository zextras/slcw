package com.zextras.transcoders.primitive;

/**
 * Helper class for transcoding a string to a char array.
 */
public class CharArrayValueTranscoder extends AbstractStringValueTranscoder<char[]> {

  public CharArrayValueTranscoder() {
  }

  /**
   * Decodes a string to a char array.
   *
   * @param value string that you want to get a char array value from.
   * @return char array - the result of transcoding operation.
   */
  public char[] decodeStringValue(final String value) {
    return value.toCharArray();
  }

  /**
   * Gives information about the class type a transcoder set with. This transcoder is used for char
   * array class.
   *
   * @return class information.
   */
  public Class<char[]> getType() {
    return char[].class;
  }
}
