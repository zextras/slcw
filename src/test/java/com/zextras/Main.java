package com.zextras;

import com.zextras.slcwBeans.User;
import com.zextras.utils.ReflectionUtils;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) {
//        SlcwClient client = new SlcwClient("localhost", 10389, "uid=admin,ou=system", "secret", "ou=system");
//        User user1 = client.getById("Name Surname", User.class);
        User newUser = new User("inetOrgPerson", "Name", "Surname", 6785949);
        Field field;
        try {
            field = newUser.getClass().getDeclaredField("phoneNumber");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        ReflectionUtils.setStringValue(field, newUser, "6890986");
    }
}
