package com.zextras.persistence.mapping.mappers;

import com.zextras.persistence.mapping.entries.Entry;

/**
 * Helper class that performs mapping operations. A Mapper relies on a separate mapper to actually
 * perform the tasks.
 * <p></p>
 * All Known Implementing Classes: {@link SlcwMapper}.
 */
public interface Mapper<V extends Entry> {

  /**
   * Maps a generic T to entry
   * @param object object to map
   * @param <T> type for object to map
   * @return mapped object
   */
  <T> V map(T object);

  <T> T map(V entry, Class<T> Clazz);
}
