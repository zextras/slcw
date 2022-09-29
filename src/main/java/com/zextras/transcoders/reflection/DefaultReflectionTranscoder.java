package com.zextras.transcoders.reflection;

import com.zextras.transcoders.primitive.*;
import java.util.*;

/**
 * Helper class that contains primitive and primitive wrapper transcoders which are used by
 * ReflectionUtils class in order to set values.
 */
public class DefaultReflectionTranscoder implements ReflectionTranscoder {

  private final SingleValueReflectionTranscoder<?> valueTranscoder;
  private final Set<SingleValueReflectionTranscoder<?>> singleValueTranscoders;

  /**
   * Creates DefaultReflectionTranscoder corresponding to a given type.
   *
   * @param type a class for which you want to get a transcoder.
   */
  public DefaultReflectionTranscoder(Class<?> type) {
    this.singleValueTranscoders = this.getDefaultSingleValueTranscoders();
    this.valueTranscoder = getSingleValueReflectionTranscoder(type);
  }

  public SingleValueReflectionTranscoder<?> getValueTranscoder() {
    return valueTranscoder;
  }

  protected SingleValueReflectionTranscoder getSingleValueReflectionTranscoder(Class<?> type) {
    Iterator var2 = this.singleValueTranscoders.iterator();

    SingleValueReflectionTranscoder transcoder;
    boolean supports;
    do {
      if (!var2.hasNext()) {
        throw new IllegalArgumentException("Unsupported type: " + type);
      }

      transcoder = (SingleValueReflectionTranscoder) var2.next();
      supports = transcoder.supports(type);
    } while (!supports);

    return transcoder;
  }

  protected Set<SingleValueReflectionTranscoder<?>> getDefaultSingleValueTranscoders() {
    Set<SingleValueReflectionTranscoder<?>> transcoders = new HashSet();
    transcoders.add(new SingleValueReflectionTranscoder(new ObjectValueTranscoder()));
    transcoders.add(new SingleValueReflectionTranscoder(new BooleanValueTranscoder()));
    transcoders.add(new SingleValueReflectionTranscoder(new BooleanValueTranscoder(true)));
    transcoders.add(new SingleValueReflectionTranscoder(new DoubleValueTranscoder()));
    transcoders.add(new SingleValueReflectionTranscoder(new DoubleValueTranscoder(true)));
    transcoders.add(new SingleValueReflectionTranscoder(new FloatValueTranscoder()));
    transcoders.add(new SingleValueReflectionTranscoder(new FloatValueTranscoder(true)));
    transcoders.add(new SingleValueReflectionTranscoder(new IntegerValueTranscoder()));
    transcoders.add(new SingleValueReflectionTranscoder(new IntegerValueTranscoder(true)));
    transcoders.add(new SingleValueReflectionTranscoder(new LongValueTranscoder()));
    transcoders.add(new SingleValueReflectionTranscoder(new LongValueTranscoder(true)));
    transcoders.add(new SingleValueReflectionTranscoder(new ShortValueTranscoder()));
    transcoders.add(new SingleValueReflectionTranscoder(new ShortValueTranscoder(true)));
    transcoders.add(new SingleValueReflectionTranscoder(new StringValueTranscoder()));
    transcoders.add(new SingleValueReflectionTranscoder(new ByteArrayValueTranscoder()));
    transcoders.add(new SingleValueReflectionTranscoder(new CharArrayValueTranscoder()));
    return transcoders;
  }

  /**
   * Decodes string value to the needed object by using a corresponding transcoder.
   *
   * @param var string that you want to get a value from.
   * @return corresponding object of transcoding operation.
   */
  @Override
  public Object decodeStringValue(String var) {
    return this.valueTranscoder.decodeStringValue(var);
  }
}
