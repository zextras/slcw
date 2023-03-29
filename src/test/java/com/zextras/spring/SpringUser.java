package com.zextras.spring;

import javax.naming.Name;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

@Entry(base = "ou=users", objectClasses = {"inetOrgPerson", "organizationalPerson"})
public class SpringUser {

  public Name getId() {
    return id;
  }

  public void setId(Name id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public Long getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(Long phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Id
  private Name id;
  @DnAttribute(value = "cn", index = 0)
  @Attribute(name = "cn")
  private String name;

  @Attribute(name = "sn")
  private String surname;

  @Attribute(name = "homePhone")
  private Long phoneNumber;

}
