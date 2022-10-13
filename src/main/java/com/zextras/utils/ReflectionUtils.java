package com.zextras.utils;

import com.zextras.persistence.SlcwException;
import com.zextras.transcoders.reflection.DefaultReflectionTranscoder;

import java.lang.reflect.Field;

/**
 * Helper util class that performs reflecting operations.
 */
public class ReflectionUtils {

  /**
   * Private constructor used to restrict someone from instantiating utility class.
   */
  private ReflectionUtils() {
    throw new SlcwException("Utility class cannot be instantiated.");
  }

  /**
   * Sets a given field with a given value using reflection.
   *
   * @param field  a field of an object that you want to modify.
   * @param object an instance of a class that you want to modify.
   * @param value  a value that you want to have after set operation.
   */
  public static void setField(final Field field, final Object object, final Object value) {
    field.setAccessible(true);
    try {
      field.set(object, value);
    } catch (final IllegalAccessException e) {
      throw new SlcwException(e.getMessage());
    }
  }

  /**
   * Sets a given field with a given string value using reflection. Uses transcoders package classes
   * in order to be able to parse values of different types from a given string.
   *
   * @param field  a field of an object that you want to modify.
   * @param object an instance of a class that you want to modify.
   * @param value  a string representation of the value that you want to have after set operation.
   */
  public static void setStringValue(final Field field, final Object object, final String value) {
    final var type = field.getType();
    final var transcoder = new DefaultReflectionTranscoder(type).getValueTranscoder();
    setField(field, object, transcoder.decodeStringValue(value));
  }

  /**
   * Sets a given field with a given byte array value using reflection.
   *
   * @param field  a field of an object that you want to modify.
   * @param object an instance of a class that you want to modify.
   * @param value  a byte array value that you want to have after set operation.
   */
  public static void setBinaryValue(final Field field, final Object object, final byte[] value) {
    setField(field, object, value);
  }
}
