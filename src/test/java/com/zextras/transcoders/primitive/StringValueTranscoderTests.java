package com.zextras.transcoders.primitive;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringValueTranscoderTests {

  @Test
  void shouldDecodeStringValue() {
    final StringValueTranscoder transcoder = new StringValueTranscoder();
    assertEquals("8", transcoder.decodeStringValue("8"));
  }

  @Test
  void shouldGetPrimitiveType() {
    final StringValueTranscoder transcoder = new StringValueTranscoder();
    assertEquals(String.class, transcoder.getType());
  }
}