package com.zextras.slcwBeans;

import com.zextras.SlcwBean;
import java.util.HashMap;
import java.util.Map;

public class User implements SlcwBean {
  private String name;
  private String surname;
  private long phoneNumber;

  public User(String name, String surname, long number) {
    this.name = name;
    this.surname = surname;
    this.phoneNumber = number;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public void setPhoneNumber(long phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public Map<String, Object> toMap() {
    Map<String, Object> userObject = new HashMap<>();
    userObject.put("givenName", this.name);
    userObject.put("sn", this.surname);
    userObject.put("homePhone", this.phoneNumber);
    return userObject;
  }
}
