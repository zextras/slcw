package com.zextras.transcoders.primitive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BooleanValueTranscoderTests {

  @Test
  void shouldDecodeStringValue() {
    final BooleanValueTranscoder transcoder = new BooleanValueTranscoder(true);
    assertTrue(transcoder.decodeStringValue("true"));
  }

  @Test
  void shouldGetPrimitiveType() {
    final BooleanValueTranscoder transcoder = new BooleanValueTranscoder(true);
    assertEquals(Boolean.TYPE, transcoder.getType());
  }

  @Test
  void shouldGetWrapper() {
    final BooleanValueTranscoder transcoder = new BooleanValueTranscoder();
    assertEquals(Boolean.class, transcoder.getType());
  }
}