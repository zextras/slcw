package com.zextras.operations.executors;

import com.zextras.SlcwBean;
import com.zextras.operations.results.OperationResult;

/**
 * An OperationExecutor relies on a separate executor to actually execute the tasks.
 * <p></p>
 */
public interface OperationExecutor {

  <T extends SlcwBean> OperationResult<T> add(T bean);

  <T extends SlcwBean> OperationResult<T> update(T bean);

  <T extends SlcwBean> OperationResult<T> delete(T bean);

  <T extends SlcwBean> OperationResult<T> search(String baseDn, String filter);
}
