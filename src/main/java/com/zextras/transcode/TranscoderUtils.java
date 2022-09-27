package com.zextras.transcode;

import java.nio.charset.StandardCharsets;

public class TranscoderUtils {
    public static byte[] utf8Encode(String value) {
        return utf8Encode(value, true);
    }

    public static byte[] utf8Encode(String value, boolean allowNull) {
        if (!allowNull && value == null) {
            throw new NullPointerException("Cannot UTF-8 encode null value");
        } else {
            return value != null ? value.getBytes(StandardCharsets.UTF_8) : null;
        }
    }

    public static String utf8Encode(byte[] value) {
        return utf8Encode(value, true);
    }

    public static String utf8Encode(byte[] value, boolean allowNull) {
        if (!allowNull && value == null) {
            throw new NullPointerException("Cannot UTF-8 encode null value");
        } else {
            return value != null ? new String(value, StandardCharsets.UTF_8) : null;
        }
    }
}
