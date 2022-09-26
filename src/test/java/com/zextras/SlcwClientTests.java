package com.zextras;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.unboundid.ldap.sdk.*;
import com.zextras.handler.SlcwException;
import com.zextras.handler.OperationResult;
import com.zextras.slcwBeans.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.zapodot.junit.ldap.EmbeddedLdapRule;
import org.zapodot.junit.ldap.EmbeddedLdapRuleBuilder;

public class SlcwClientTests {
  @Rule
  public EmbeddedLdapRule embeddedLdapRule =
      EmbeddedLdapRuleBuilder.newInstance()
          .usingDomainDsn("dc=example,dc=com")
          .importingLdifs("in-memory-server.ldif")
          .build();

  @Test
  public void shouldGetById() throws LDAPException {
    final SlcwClient client =
        new SlcwClient(embeddedLdapRule.unsharedLdapConnection(), "dc=example,dc=com");
    final User expectedUser = new User("inetOrgPerson", "Name", "Surname", 6785949);
    client.add(expectedUser);
    final User actualUser = client.getById("Name Surname", User.class);
    assertEquals(expectedUser, actualUser);
  }

  @Test
  public void shouldReturnSuccessOnAddOperation() throws LDAPException {
    final SlcwClient client =
        new SlcwClient(embeddedLdapRule.unsharedLdapConnection(), "dc=example,dc=com");
    final User user = new User("inetOrgPerson", "Name", "Surname", 6785949);
    final OperationResult result = client.add(user);
    assertEquals("0 (success)", result.getStringRepresentation());
  }

  @Test
  public void shouldReturnSuccessOnDeleteOperation() throws LDAPException {
    final SlcwClient client =
        new SlcwClient(embeddedLdapRule.unsharedLdapConnection(), "dc=example,dc=com");
    final User user = new User("inetOrgPerson", "Name", "Surname", 6785949);
    client.add(user);

    final User presentUser = client.getById(user.getUid(), User.class);
    assertEquals(user, presentUser);

    final OperationResult result = client.delete(user);
    assertEquals("0 (success)", result.getStringRepresentation());

    Assertions.assertThrows(SlcwException.class, () -> client.getById(user.getUid(), User.class));
  }

  @Test
  public void shouldReturnSuccessOnModifyOperation() throws LDAPException {
    final SlcwClient client =
        new SlcwClient(embeddedLdapRule.unsharedLdapConnection(), "dc=example,dc=com");
    final User user = new User("inetOrgPerson", "Name", "Surname", 6785949);

    client.add(user);
    final User presentUser = client.getById(user.getUid(), User.class);
    assertEquals(user.getPhoneNumber(), presentUser.getPhoneNumber());

    user.setPhoneNumber(11111111);
    final OperationResult result = client.update(user);
    assertEquals("0 (success)", result.getStringRepresentation());

    final User modifiedUser = client.getById(user.getUid(), User.class);
    assertEquals(user.getPhoneNumber(), modifiedUser.getPhoneNumber());
  }
}
