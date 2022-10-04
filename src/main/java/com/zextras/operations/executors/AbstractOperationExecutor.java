package com.zextras.operations.executors;

import com.zextras.persistence.mapping.entries.Entry;

/**
 * This class provides a skeletal implementation of the OperationExecutor interface, to minimize the
 * effort required to implement this interface. The documentation for each non-abstract method in
 * this class describes its implementation in detail. Each of these methods may be overridden if the
 * OperationExecutor being implemented admits a more efficient implementation.
 */
public abstract class AbstractOperationExecutor<T extends Entry> implements OperationExecutor<T> {

  protected AbstractOperationExecutor() {

  }
}
