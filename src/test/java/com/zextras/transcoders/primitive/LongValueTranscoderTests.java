package com.zextras.transcoders.primitive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LongValueTranscoderTests {

  @Test
  void shouldDecodeStringValue() {
    final LongValueTranscoder transcoder = new LongValueTranscoder(true);
    assertEquals(8, transcoder.decodeStringValue("8"));
  }

  @Test
  void shouldGetPrimitiveType() {
    final LongValueTranscoder transcoder = new LongValueTranscoder(true);
    assertEquals(Long.TYPE, transcoder.getType());
  }

  @Test
  void shouldGetWrapper() {
    final LongValueTranscoder transcoder = new LongValueTranscoder();
    assertEquals(Long.class, transcoder.getType());
  }
}