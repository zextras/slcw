package com.zextras;

import com.zextras.slcwBeans.User;

public class Main {
    public static void main(String[] args) {
        SlcwClient client = new SlcwClient("localhost", 10389, "uid=admin,ou=system", "secret", "ou=system");
        User user1 = client.getById("Name Surname", User.class);
    }
}
