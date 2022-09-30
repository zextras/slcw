package com.zextras.transcoders.primitive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DoubleValueTranscoderTests {

  @Test
  void shouldDecodeStringValue() {
    final DoubleValueTranscoder transcoder = new DoubleValueTranscoder(true);
    assertEquals(3.12, transcoder.decodeStringValue("3.12"));
  }

  @Test
  void shouldGetPrimitiveType() {
    final DoubleValueTranscoder transcoder = new DoubleValueTranscoder(true);
    assertEquals(Double.TYPE, transcoder.getType());
  }

  @Test
  void shouldGetWrapper() {
    final DoubleValueTranscoder transcoder = new DoubleValueTranscoder();
    assertEquals(Double.class, transcoder.getType());
  }
}