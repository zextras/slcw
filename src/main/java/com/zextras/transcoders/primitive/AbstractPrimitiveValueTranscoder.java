package com.zextras.transcoders.primitive;

/**
 * Helper class for transcoding a string to primitive types.
 */
public abstract class AbstractPrimitiveValueTranscoder<T> extends AbstractStringValueTranscoder<T> {

  private boolean primitive;

  protected AbstractPrimitiveValueTranscoder() {
  }

  public boolean isPrimitive() {
    return this.primitive;
  }

  public void setPrimitive(final boolean b) {
    this.primitive = b;
  }
}
