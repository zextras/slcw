package com.zextras.transcoders.primitive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FloatValueTranscoderTests {

  @Test
  void shouldDecodeStringValue() {
    final DoubleValueTranscoder transcoder = new DoubleValueTranscoder(true);
    assertEquals(8.5F, transcoder.decodeStringValue("8.5F"));
  }

  @Test
  void shouldGetPrimitiveType() {
    final FloatValueTranscoder transcoder = new FloatValueTranscoder(true);
    assertEquals(Float.TYPE, transcoder.getType());
  }

  @Test
  void shouldGetWrapper() {
    final FloatValueTranscoder transcoder = new FloatValueTranscoder();
    assertEquals(Float.class, transcoder.getType());
  }
}