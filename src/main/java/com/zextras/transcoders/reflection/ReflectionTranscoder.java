package com.zextras.transcoders.reflection;

/**
 * A ReflectionTranscoder relies on a separate transcoder to actually execute the tasks. Used by
 * ReflectionUtils class in order to set values.
 * <p></p>
 * All Known Implementing Classes: {@link DefaultReflectionTranscoder},
 * {@link SingleValueReflectionTranscoder}.
 */
public interface ReflectionTranscoder {

  Object decodeStringValue(String var);
}
