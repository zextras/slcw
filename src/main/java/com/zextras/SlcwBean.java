package com.zextras;

//todo fix when implemented

import com.zextras.persistence.annotations.Table;
import java.util.Objects;

/**
 * The SlcwBean class represents an object that would be used by {@linkplain SlcwClient} in order to
 * provide CRUD operations on it.
 */
public abstract class SlcwBean {

  private final String dn;

  protected SlcwBean(String dn) {
    this.dn = dn;
  }

  public String getDn() {
    return this.dn;
  }

}
