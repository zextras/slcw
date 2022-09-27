package com.zextras.utils;

import com.zextras.transcode.reflection.DefaultReflectionTranscoder;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static void setField(Field field, Object object, Object value) {
        try {
            field.set(object, value);
        } catch (IllegalAccessException var4) {
            throw new IllegalArgumentException(var4);
        }
    }

    public static void setStringValue(Field field, Object object, String value) {
        field.setAccessible(true);
        var type = field.getType();
        var transcoder = new DefaultReflectionTranscoder(type).getValueTranscoder();
        setField(field, object, transcoder.decodeStringValue(value));
    }

    public static void setBinaryValue(Field field, Object object, byte[] value) {
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
