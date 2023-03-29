package com.zextras;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.*;
import com.zextras.persistence.SlcwException;
import com.zextras.operations.results.OperationResult;
import com.zextras.slcwBeans.User;
import java.util.UUID;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.zapodot.junit.ldap.EmbeddedLdapRule;
import org.zapodot.junit.ldap.EmbeddedLdapRuleBuilder;

public class SlcwClientIT {

  private static final String BASE_DN = "dc=example, dc=com";
  private static final Integer PORT = 3000;
  private static final String USER_DN = "cn=test, " + BASE_DN;
  private static final String PASSWORD = "password";
  private static SlcwClient<User> client;

  @BeforeClass
  public static void setUp() throws LDAPException {
    InMemoryListenerConfig listenerConfig = InMemoryListenerConfig.createLDAPConfig("default", PORT);
    InMemoryDirectoryServerConfig serverConfig = new InMemoryDirectoryServerConfig(new DN(BASE_DN));
    serverConfig.setListenerConfigs(listenerConfig);
    InMemoryDirectoryServer server = new InMemoryDirectoryServer(serverConfig);
    server.importFromLDIF(true, "src/test/resources/in-memory-server.ldif");
    server.startListening();
    serverConfig.addAdditionalBindCredentials(USER_DN, PASSWORD);
    serverConfig.setSchema(null);
    LDAPConnectionPool ldapConnectionPool = new LDAPConnectionPool(
        new LDAPConnection("127.0.0.1", PORT, USER_DN, PASSWORD), 1);
    client = new SlcwClient<>(ldapConnectionPool, BASE_DN, User.class);
  }

  @Test
  public void shouldGetById() {
    final User expectedUser = new User(UUID.randomUUID().toString(), "Surname", 6785949);
    client.add(expectedUser);
    final OperationResult<User> actualUser = client.search(expectedUser);
    assertEquals(expectedUser, actualUser.getData().get(0));
  }

  @Test
  public void shouldReturnSuccessOnAddOperation() {
    final User user = new User(UUID.randomUUID().toString(), "Surname", 6785949);
    final OperationResult<User> result = client.add(user);
    assertEquals("0 (success)", result.toString());
  }

  @Test
  public void shouldReturnSuccessOnDeleteOperation() {
    final User user = new User( UUID.randomUUID().toString(), "Surname", 6785949);
    client.add(user);
    User searchUser = new User();
    searchUser.setId(user.getId());
    final User actualUser = client.search(searchUser).getData().get(0);
    // DN is unique
    assertEquals(user.getDn(), actualUser.getDn());

    final OperationResult<User> deleteResult = client.delete(user);
    assertEquals("0 (success)", deleteResult.toString());
  }

  @Test
  public void shouldReturnSuccessOnUpdateOperation() throws LDAPException {
    final User user = new User(UUID.randomUUID().toString(), "Surname", 6785949);

    client.add(user);
    final OperationResult<User> operationResult = client.search(user);
    final User presentUser = operationResult.getData().get(0);
    assertEquals(user.getPhoneNumber(), presentUser.getPhoneNumber());

    user.setPhoneNumber(11111111);
    final OperationResult<User> result = client.update(user);
    assertEquals("0 (success)", result.toString());
    final OperationResult<User> search2 = client.search(user);
    final User modifiedUser = search2.getData().get(0);
    assertEquals(user.getPhoneNumber(), modifiedUser.getPhoneNumber());
  }
}
