package com.zextras;

//todo fix when implemented
/**
 * The SlcwBean class represents an object that would be used by {@linkplain SlcwClient} in order to
 * provide CRUD operations on it.
 */
public abstract class SlcwBean {

  protected String dn;

  public String getDn() {
    return this.dn;
  };

  void setDn(String dn) {
    this.dn = dn;
  }

}
