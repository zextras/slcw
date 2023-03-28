package com.zextras.slcwBeans;

import com.zextras.SlcwBean;
import com.zextras.persistence.annotations.*;

import java.util.Objects;

@Entity
@Table(objectClass = "inetOrgPerson", property = "ou", name = "users")
public class User extends SlcwBean {

  @Id(name = "cn")
  private String id;
  @Column(name = "givenName")
  private String name;
  @Column(name = "sn")
  private String surname;
  @Column(name = "homePhone")
  private Long phoneNumber;

  public User(String dn, String name, String surname, long phoneNumber) {
    super(dn);
    this.id = name + " " + surname;
    this.name = name;
    this.surname = surname;
    this.phoneNumber = phoneNumber;
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

  public void setId(String id) {
    this.id = id;
  }


  public String getSurname() {
    return surname;
  }

  public long getPhoneNumber() {
    return phoneNumber;
  }

  public String getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return id.equals(user.id) && Objects.equals(name, user.name) && Objects.equals(surname,
        user.surname) && Objects.equals(phoneNumber, user.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, surname, phoneNumber);
  }

  @Override
  public String getDn() {
    return id;
  }
}
