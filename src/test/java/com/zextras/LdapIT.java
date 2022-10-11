package com.zextras;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class LdapIT {

  @Container
  protected GenericContainer container =
      new GenericContainer<>("carbonio/ce-ldap-u20:latest")
          .withCreateContainerCmdModifier(it -> it.withHostName("ldap.mail.local"))
          .withExposedPorts(389)
          .withImagePullPolicy(PullPolicy.defaultPolicy());
}
