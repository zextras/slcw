package com.zextras.operations.executors;

import com.zextras.SlcwBean;
import com.zextras.operations.results.OperationResult;

/**
 * An OperationExecutor relies on a separate executor to actually execute the tasks.
 * <p></p>
 */
public interface OperationExecutor<T extends SlcwBean> {

  /**
   * Creates the object in the database.
   *
   * @param bean managed object
   * @return result of operation, data always empty
   */
  OperationResult<T> add(T bean);

  /**
   * Updates the object in database according to stored data
   * @param bean managed object
   * @return result of operation, data always empty
   */
  OperationResult<T> update(T bean);

  /**
   * Deleted the object in database
   * @param bean managed object
   * @return result of operation, data always empty
   */  OperationResult<T> delete(T bean);

  /**
   * Searches objects in database according to bean information
   * @param bean managed object
   * @return result of operation, data contains result of search
   */
  OperationResult<T> search(T bean);
}
