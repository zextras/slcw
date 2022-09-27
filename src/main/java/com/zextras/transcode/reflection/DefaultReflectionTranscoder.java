package com.zextras.transcode.reflection;

import com.zextras.transcode.primitive.*;

import java.util.*;

public class DefaultReflectionTranscoder implements ReflectionTranscoder {
    private SingleValueReflectionTranscoder<?> valueTranscoder;
    private Set<SingleValueReflectionTranscoder<?>> singleValueTranscoders;

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

                transcoder = (SingleValueReflectionTranscoder)var2.next();
                supports = transcoder.supports(type);
            } while(!supports);

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

    @Override
    public Object decodeStringValue(String var) {
        return this.valueTranscoder.decodeStringValue(var);
    }
}
