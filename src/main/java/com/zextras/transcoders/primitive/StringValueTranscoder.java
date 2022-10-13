package com.zextras.transcoders.primitive;

/**
 * Helper class for transcoding strings.
 */
public class StringValueTranscoder extends AbstractStringValueTranscoder<String> {

  public StringValueTranscoder() {
  }

  /**
   * Returns a given value as no need to transcode.
   *
   * @param value string.
   * @return a given value.
   */
  public String decodeStringValue(final String value) {
    return value;
  }

  /**
   * Gives information about the class type a transcoder set with. This transcoder is used for
   * String class.
   *
   * @return class information.
   */
  public Class<String> getType() {
    return String.class;
  }
}
