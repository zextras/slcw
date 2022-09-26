package com.zextras.utils;

import java.lang.reflect.Field;

public class ReflectionUtils {
    public static void setValue(Field field, Object object, String value) {
        field.setAccessible(true);
        var type = field.getType().getName();
        switch (type) {
            case "int":
            case "java.lang.Integer":
                setInt(field, object, value);
                break;
            case "long":
            case "java.lang.Long":
                setLong(field, object, value);
                break;
            case "double":
            case "java.lang.Double":
                setDouble(field, object, value);
                break;
            case "float":
            case "java.lang.Float":
                setFloat(field, object, value);
                break;
            case "boolean":
            case "java.lang.Boolean":
                setBoolean(field, object, value);
                break;
            case "short":
            case "java.lang.Short":
                setShort(field, object, value);
                break;
            case "byte":
            case "java.lang.Byte":
                setByte(field, object, value);
                break;
            default:
                setString(field, object, value);
        }
    }

    public static void setBinaryValue(Field field, Object object, byte[] value) {
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setByte(Field field, Object object, String value)  {
        byte newValue = Byte.parseByte(value);
        try {
            field.setByte(object, newValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setShort(Field field, Object object, String value) {
        short newValue = Short.parseShort(value);
        try {
            field.setShort(object, newValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setInt(Field field, Object object, String value) {
        int newValue = Integer.parseInt(value);
        try {
            field.setInt(object, newValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setLong(Field field, Object object, String value) {
        long newValue = Long.parseLong(value);
        try {
            field.setLong(object, newValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setFloat(Field field, Object object, String value) {
        float newValue = Float.parseFloat(value);
        try {
            field.setFloat(object, newValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setDouble(Field field, Object object, String value) {
        double newValue = Double.parseDouble(value);
        try {
            field.setDouble(object, newValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setBoolean(Field field, Object object, String value) {
        boolean newValue = Boolean.parseBoolean(value);
        try {
            field.setBoolean(object, newValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setString(Field field, Object object, String value) {
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
