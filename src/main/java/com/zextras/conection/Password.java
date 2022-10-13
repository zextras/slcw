package com.zextras.conection;

import com.zextras.utils.TranscoderUtils;

/**
 * A class is used to store password information.
 */
public class Password {

  private final byte[] bytes;

  /**
   * Creates password from a given string.
   *
   * @param password string value.
   */
  public Password(final String password) {
    if (password == null) {
      throw new NullPointerException("Password cannot be null.");
    } else {
      this.bytes = TranscoderUtils.utf8Encode(password, false);
    }
  }

  /**
   * Creates password from a given byte array.
   *
   * @param password byte array value.
   */
  public Password(final byte[] password) {
    if (password == null) {
      throw new NullPointerException("Password cannot be null.");
    } else {
      this.bytes = password;
    }
  }

  public byte[] getBytes() {
    return this.bytes;
  }

  public String getString() {
    return TranscoderUtils.utf8Encode(this.bytes, false);
  }
}
