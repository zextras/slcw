package com.zextras.transcoders.primitive;

/**
 * Helper class for transcoding a string and binary data to an object class, used by abstract
 * classes.
 */
public class ObjectValueTranscoder implements ValueTranscoder<Object> {

  public ObjectValueTranscoder() {
  }

  /**
   * Transcode a string to an object.
   *
   * @param value a string you want to have an object from.
   * @return object.
   */
  public Object decodeStringValue(String value) {
    return value;
  }

  /**
   * Transcode a byte array to an object.
   *
   * @param value a byte array you want to have an object from.
   * @return object.
   */
  public Object decodeBinaryValue(byte[] value) {
    return value;
  }

  /**
   * Gives information about the class type a transcoder set with.
   *
   * @return class information.
   */
  public Class<Object> getType() {
    return Object.class;
  }
}
