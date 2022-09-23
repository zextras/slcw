package com.zextras.slcwBeans;

import com.zextras.SlcwBean;
import com.zextras.slcwPersistence.annotations.Attribute;
import com.zextras.slcwPersistence.annotations.Entity;
import com.zextras.slcwPersistence.annotations.ObjectClass;
import com.zextras.slcwPersistence.annotations.UID;

@Entity(name = "users")
public class User implements SlcwBean {
  @UID(name = "cn")
  private String uid;
  @ObjectClass
  private String objectClass;
  @Attribute(name = "givenName")
  private String name;
  @Attribute(name = "sn")
  private String surname;
  @Attribute(name = "homePhone")
  private long phoneNumber;
  private int anotherField;

  public User() {}

  public User(String objectClass, String name, String surname, long number) {
    this.uid = name + " " + surname;
    this.objectClass = objectClass;
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

  public String getName() {
    return name;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public void setObjectClass(String objectClass) {
    this.objectClass = objectClass;
  }

  public String getSurname() {
    return surname;
  }

  public long getPhoneNumber() {
    return phoneNumber;
  }

  public String getObjectClass() {
    return objectClass;
  }

  public String getUid() {
    return uid;
  }
}
