package com.zextras.transcoders.reflection;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class DefaultReflectionTranscoderTests {

  @Test
  void getValueTranscoder() {
    final DefaultReflectionTranscoder transcoder = new DefaultReflectionTranscoder(byte[].class);
    final SingleValueReflectionTranscoder<?> valueTranscoder = transcoder.getValueTranscoder();
    assertEquals(byte[].class, valueTranscoder.getType());
  }

  @Test
  void decodeStringValue() {
    final DefaultReflectionTranscoder transcoder = new DefaultReflectionTranscoder(byte[].class);
    final SingleValueReflectionTranscoder<?> valueTranscoder = transcoder.getValueTranscoder();
    assertArrayEquals("hello".getBytes(StandardCharsets.UTF_8),
        (byte[]) valueTranscoder.decodeStringValue("hello"));
  }
}