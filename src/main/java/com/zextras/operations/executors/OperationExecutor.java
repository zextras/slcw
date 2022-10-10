package com.zextras.operations.executors;

import com.zextras.operations.results.OperationResult;
import com.zextras.persistence.mapping.entries.Entry;

/**
 * An OperationExecutor relies on a separate executor to actually execute the tasks.
 * <p></p>
 * All Known Implementing Classes: {@link AbstractOperationExecutor},
 * {@link LdapOperationExecutor}.
 */
public interface OperationExecutor<T extends Entry> {

  OperationResult executeAddOperation(T entry);

  OperationResult executeUpdateOperation(T entry);

  OperationResult executeDeleteOperation(T entry);

  OperationResult executeGetOperation(T entry);
}
