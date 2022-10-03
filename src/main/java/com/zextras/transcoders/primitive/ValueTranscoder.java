package com.zextras.transcoders.primitive;

/**
 * A ValueTranscoder relies on a separate transcoder to actually execute the tasks.
 * <p></p>
 * All Known Implementing Classes: {@link AbstractBinaryValueTranscoder},
 * {@link AbstractPrimitiveValueTranscoder}, {@link AbstractStringValueTranscoder},
 * {@link BooleanValueTranscoder}, {@link ByteArrayValueTranscoder},
 * {@link CharArrayValueTranscoder}, {@link DoubleValueTranscoder},
 * {@link FloatValueTranscoder}, {@link IntegerValueTranscoder},
 * {@link LongValueTranscoder}, {@link ObjectValueTranscoder},
 * {@link ShortValueTranscoder}, {@link StringValueTranscoder}.
 */
public interface ValueTranscoder<T> {

  T decodeStringValue(String var1);

  T decodeBinaryValue(byte[] var1);

  Class<T> getType();
}
