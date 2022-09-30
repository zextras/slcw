package com.zextras.transcoders.primitive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ShortValueTranscoderTests {

  @Test
  void shouldDecodeStringValue() {
    final ShortValueTranscoder transcoder = new ShortValueTranscoder(true);
    assertEquals((short) 8, transcoder.decodeStringValue("8"));
  }

  @Test
  void shouldGetPrimitiveType() {
    final ShortValueTranscoder transcoder = new ShortValueTranscoder(true);
    assertEquals(Short.TYPE, transcoder.getType());
  }

  @Test
  void shouldGetWrapper() {
    final ShortValueTranscoder transcoder = new ShortValueTranscoder();
    assertEquals(Short.class, transcoder.getType());
  }
}