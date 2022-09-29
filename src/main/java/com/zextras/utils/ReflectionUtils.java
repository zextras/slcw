package com.zextras.utils;

import com.zextras.transcoders.reflection.DefaultReflectionTranscoder;

import java.lang.reflect.Field;

public class ReflectionUtils {

  public static void setField(Field field, Object object, Object value) {
    field.setAccessible(true);
    try {
      field.set(object, value);
    } catch (IllegalAccessException var) {
      throw new IllegalArgumentException(var);
    }
  }

  public static void setStringValue(Field field, Object object, String value) {
    var type = field.getType();
    var transcoder = new DefaultReflectionTranscoder(type).getValueTranscoder();
    setField(field, object, transcoder.decodeStringValue(value));
  }

  public static void setBinaryValue(Field field, Object object, byte[] value) {
    setField(field, object, value);
  }
}
