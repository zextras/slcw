package com.zextras;

//todo fix when implemented
/**
 * The SlcwBean class represents an object that would be used by {@linkplain SlcwClient} in order to
 * provide CRUD operations on it.
 */
public abstract class SlcwBean {

  protected final String dn;

  protected SlcwBean(String dn) {
    this.dn = dn;
  }

  public String getDn() {
    return this.dn;
  };

}
