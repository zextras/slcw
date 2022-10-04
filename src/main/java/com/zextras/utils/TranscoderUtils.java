package com.zextras.utils;

import com.zextras.persistence.SlcwException;
import java.nio.charset.StandardCharsets;

/**
 * Helper util class that performs transcoding operations.
 */
public class TranscoderUtils {

  /**
   * Private constructor used to restrict someone from instantiating utility class.
   */
  private TranscoderUtils() {
    throw new SlcwException("Utility class cannot been instantiated.");
  }

  /**
   * Encodes string value to byte array using Eight-bit UCS Transformation Format.
   *
   * @param value a string that you want to encode.
   * @return encoded byte array.
   */
  public static byte[] utf8Encode(String value) {
    return utf8Encode(value, true);
  }

  /**
   * Encodes string value to byte array using Eight-bit UCS Transformation Format.
   *
   * @param value     a string that you want to encode.
   * @param allowNull expected true if allows and false if not.
   * @return encoded byte array.
   */
  public static byte[] utf8Encode(String value, boolean allowNull) {
    if (!allowNull && value == null) {
      throw new NullPointerException("Cannot UTF-8 encode null value");
    } else {
      return value != null ? value.getBytes(StandardCharsets.UTF_8) : null;
    }
  }

  /**
   * Encodes byte array value to a string using Eight-bit UCS Transformation Format.
   *
   * @param value byte array you want to encode.
   * @return new string.
   */
  public static String utf8Encode(byte[] value) {
    return utf8Encode(value, true);
  }

  /**
   * Encodes byte array value to a string using Eight-bit UCS Transformation Format.
   *
   * @param value     byte array you want to encode.
   * @param allowNull expected true if allows and false if not.
   * @return new string.
   */
  public static String utf8Encode(byte[] value, boolean allowNull) {
    if (!allowNull && value == null) {
      throw new NullPointerException("Cannot UTF-8 encode null value");
    } else {
      return value != null ? new String(value, StandardCharsets.UTF_8) : null;
    }
  }
}
