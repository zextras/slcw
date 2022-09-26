package com.zextras;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) {

        User user = new User("inetOrgPerson","With photo", "Surname", 6785949);
        File fi = new File("/home/user/IdeaProjects/sentry/src/main/resources/Sentry INIT,  With Sentry and Without Sentry.png");
        byte[] fileContent = null;
        try {
            fileContent = Files.readAllBytes(fi.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        user.setPhoto(fileContent);

        SlcwClient client = new SlcwClient("localhost", 10389, "uid=admin,ou=system", "secret", "ou=system");
//        client.add(user);
        User actual = client.getById("With photo Surname", User.class);
    }
}
