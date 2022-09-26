package com.zextras;

import javax.naming.NamingException;

public class Main {
    public static void main(String[] args) throws NamingException {
        SlcwClient client = new SlcwClient("localhost", 10389, "uid=admin,ou=system", "secret");
//        client.getByUID("name surname", User.class);
    }
}
