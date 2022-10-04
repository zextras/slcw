package com.zextras.persistence.mapping.mappers;

import com.zextras.persistence.mapping.entries.Entry;

/**
 * Helper class that performs mapping operations. A Mapper relies on a separate mapper to actually
 * perform the tasks.
 * <p></p>
 * All Known Implementing Classes: {@link SlcwMapper}.
 */
public interface Mapper<V extends Entry> {

  <T> void map(T object, V entry);

  <T> void map(V entry, T object);
}
