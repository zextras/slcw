package com.zextras.operations.executors;

import com.zextras.SlcwBean;
import com.zextras.operations.results.OperationResult;

/**
 * An OperationExecutor relies on a separate executor to actually execute the tasks.
 * <p></p>
 */
public interface OperationExecutor<T extends SlcwBean> {

  OperationResult<T> add(T bean);

  OperationResult<T> update(T bean);

  OperationResult<T> delete(T bean);

  OperationResult<T> search(String baseDn, String filter);

  OperationResult<T> search(String uid);
}
