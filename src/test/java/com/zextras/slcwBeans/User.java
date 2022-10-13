package com.zextras.slcwBeans;

import com.zextras.SlcwBean;
import com.zextras.persistence.annotations.*;

import java.util.Objects;

@Entity
@Table(property = "ou", name = "people")
public class User extends SlcwBean {

  @Id(name = "uid")
  private String id;
  @ObjectClass
  private String objectClass;
  @Column(name = "cn")
  private String name;
  @Column(name = "sn")
  private String surname;
  @Column(name = "homePhone")
  private Long phoneNumber;
  private int anotherField;

  public User() {
  }

  public User(final String objectClass, final String name, final String surname,
      final long number) {
    this.id = name + " " + surname;
    this.objectClass = objectClass;
    this.name = name;
    this.surname = surname;
    this.phoneNumber = number;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setSurname(final String surname) {
    this.surname = surname;
  }

  public void setPhoneNumber(final long phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getName() {
    return name;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public void setObjectClass(final String objectClass) {
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

  public String getId() {
    return id;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    final User user = (User) o;
    return anotherField == user.anotherField && id.equals(user.id) && Objects.equals(objectClass,
        user.objectClass) && Objects.equals(name, user.name) && Objects.equals(surname,
        user.surname) && Objects.equals(phoneNumber, user.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, objectClass, name, surname, phoneNumber, anotherField);
  }
}
