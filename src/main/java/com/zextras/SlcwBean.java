package com.zextras;

//todo fix when implemented

import com.unboundid.ldap.sdk.LDAPConnectionPool;
import com.zextras.operations.executors.OperationExecutor;
import com.zextras.operations.results.OperationResult;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.annotations.Id;
import com.zextras.persistence.annotations.Table;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * The SlcwBean class represents an object that would be used by {@linkplain SlcwClient} in order to
 * provide CRUD operations on it.
 */
public abstract class SlcwBean {

  private String dn;


  public String getDn() {
    return this.dn;
  }

  public String getUid() {
    try {
      return (String) Arrays.stream(this.getClass().getDeclaredFields()).filter(field ->
      {
        field.setAccessible(true);
        return field.isAnnotationPresent(Id.class);
      }).findFirst().get().get(this);
    } catch (IllegalAccessException e) {
      throw new SlcwException(e.getMessage());
    }
  }

  public void setDn(String dn) {
    this.dn = dn;
  }

}
