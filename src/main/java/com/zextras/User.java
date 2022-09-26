package com.zextras;

import com.zextras.slcwPersistence.annotations.*;

import java.util.Objects;

@Entity
@Table(property = "ou", name = "users")
public class User {
    @Id(name = "cn")
    private String uid;
    @ObjectClass
    private String objectClass;
    @Column(name = "givenName")
    private String name;
    @Column(name = "sn")
    private String surname;
    @Column(name = "homePhone")
    private long phoneNumber;
    @Column(name = "photo", binary = true)
    private byte[] photo;
    private int anotherField;
    private Integer integerField;

    public User() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return anotherField == user.anotherField && uid.equals(user.uid) && Objects.equals(objectClass, user.objectClass) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(phoneNumber, user.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, objectClass, name, surname, phoneNumber, anotherField);
    }

    public int getAnotherField() {
        return anotherField;
    }

    public Integer getIntegerField() {
        return integerField;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
