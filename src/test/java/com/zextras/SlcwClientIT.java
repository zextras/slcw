package com.zextras;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.unboundid.ldap.sdk.*;
import com.zextras.persistence.SlcwException;
import com.zextras.operations.results.OperationResult;
import com.zextras.slcwBeans.User;
import java.util.UUID;
import org.junit.Rule;
import org.junit.Test;
import org.zapodot.junit.ldap.EmbeddedLdapRule;
import org.zapodot.junit.ldap.EmbeddedLdapRuleBuilder;

public class SlcwClientIT {

  @Rule
  public EmbeddedLdapRule embeddedLdapRule =
      EmbeddedLdapRuleBuilder.newInstance()
          .usingDomainDsn("dc=example,dc=com")
          .importingLdifs("in-memory-server.ldif")
          .build();

  @Test
  public void shouldGetById() throws LDAPException {
    final SlcwClient client = SlcwClient.initialize(embeddedLdapRule.unsharedLdapConnection(),"dc=example,dc=com", 1);
    final String uid = UUID.randomUUID().toString();
    final User expectedUser = new User("uid=" + uid + ", dc=example,dc=com", "Name", "Surname", 6785949);
    final OperationResult<User> userOperationResult = client.add(expectedUser);
    final OperationResult<SlcwBean> actualUser = client.search(expectedUser.getDn(), Filter.createEqualityFilter("objectClass", "person").toString());
    assertEquals(expectedUser, actualUser.getData().get(0));
  }

  @Test
  public void shouldReturnSuccessOnAddOperation() throws LDAPException {
    final SlcwClient client = SlcwClient.initialize(embeddedLdapRule.unsharedLdapConnection(),"dc=example,dc=com", 1);
    final String uid = UUID.randomUUID().toString();
    final User user = new User("uid=" + uid + ", dc=example,dc=com", "Name", "Surname", 6785949);
    final OperationResult<User> result = client.add(user);
    assertEquals("0 (success)", result.toString());
  }

  @Test
  public void shouldReturnSuccessOnDeleteOperation() throws LDAPException {
    final SlcwClient client = SlcwClient.initialize(embeddedLdapRule.unsharedLdapConnection(),"dc=example,dc=com", 1);
    final String uid = UUID.randomUUID().toString();
    final User user = new User( "uid=" + uid + ", dc=example,dc=com", "Name", "Surname", 6785949);
    client.add(user);
    final OperationResult<User> result = client.search(user.getId(), "");
    assertEquals(user, result.getData().get(0));

    final OperationResult<User> deleteResult = client.delete(user);
    assertEquals("0 (success)", deleteResult.toString());

    assertThrows(SlcwException.class, () -> client.search(user.getId(),""));
  }

  @Test
  public void shouldReturnSuccessOnUpdateOperation() throws LDAPException {
    final SlcwClient client = SlcwClient.initialize(embeddedLdapRule.unsharedLdapConnection(),"dc=example,dc=com", 1);
    final String uid = UUID.randomUUID().toString();
    final User user = new User("uid=" + uid + ", dc=example,dc=com", "Name", "Surname", 6785949);

    client.add(user);
    final OperationResult<User> operationResult = client.search(user.getId(), "");
    final User presentUser = operationResult.getData().get(0);
    assertEquals(user.getPhoneNumber(), presentUser.getPhoneNumber());

    user.setPhoneNumber(11111111);
    final OperationResult<User> result = client.update(user);
    assertEquals("0 (success)", result.toString());
    final OperationResult<User> search2 = client.search(user.getId(), "");
    final User modifiedUser = search2.getData().get(0);
    assertEquals(user.getPhoneNumber(), modifiedUser.getPhoneNumber());
  }
}
