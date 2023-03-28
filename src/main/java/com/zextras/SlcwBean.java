package com.zextras;

//todo fix when implemented

import com.zextras.operations.executors.OperationExecutor;
import com.zextras.operations.results.OperationResult;
import com.zextras.persistence.annotations.Table;
import java.util.Objects;

/**
 * The SlcwBean class represents an object that would be used by {@linkplain SlcwClient} in order to
 * provide CRUD operations on it.
 */
public abstract class SlcwBean {

  private final String dn;
  private boolean isCreated = false;

  protected SlcwBean(String dn) {
    this.dn = dn;
  }

  protected SlcwBean(String dn, OperationExecutor operationExecutor) {
    this.dn = dn;
  }

  public String getDn() {
    return this.dn;
  }

}
