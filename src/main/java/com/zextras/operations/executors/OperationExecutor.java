package com.zextras.operations.executors;

import com.zextras.SlcwBean;
import com.zextras.operations.results.OperationResult;
import com.zextras.persistence.mapping.entries.Entry;

/**
 * An OperationExecutor relies on a separate executor to actually execute the tasks.
 * <p></p>
 * {@link LdapOperationExecutor}.
 */
public interface OperationExecutor {

  <T extends SlcwBean> OperationResult<T> executeAddOperation(T bean);

  <T extends SlcwBean> OperationResult<T> executeUpdateOperation(T bean);

  <T extends SlcwBean> OperationResult<T> executeDeleteOperation(T bean);

  <T extends SlcwBean> OperationResult<T> executeSearchOperation(String baseDn, String filter);
}
