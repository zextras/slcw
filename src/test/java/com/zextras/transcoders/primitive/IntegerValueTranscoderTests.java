package com.zextras.transcoders.primitive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IntegerValueTranscoderTests {

  @Test
  void shouldDecodeStringValue() {
    final IntegerValueTranscoder transcoder = new IntegerValueTranscoder(true);
    assertEquals(8, transcoder.decodeStringValue("8"));
  }

  @Test
  void shouldGetPrimitiveType() {
    final IntegerValueTranscoder transcoder = new IntegerValueTranscoder(true);
    assertEquals(Integer.TYPE, transcoder.getType());
  }

  @Test
  void shouldGetWrapper() {
    final IntegerValueTranscoder transcoder = new IntegerValueTranscoder();
    assertEquals(Integer.class, transcoder.getType());
  }
}