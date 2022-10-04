package com.zextras.operations.executors;

import com.zextras.operations.results.OperationResult;
import com.zextras.persistence.mapping.entries.SlcwEntry;

/**
 * An OperationExecutor relies on a separate executor to actually execute the tasks.
 * <p></p>
 * All Known Implementing Classes: {@link AbstractOperationExecutor},
 * {@link LdapOperationExecutor}.
 */
public interface OperationExecutor {

  OperationResult executeAddOperation(SlcwEntry entry);

  OperationResult executeUpdateOperation(SlcwEntry entry);

  OperationResult executeDeleteOperation(SlcwEntry entry);

  OperationResult executeGetOperation(SlcwEntry entry);
}
