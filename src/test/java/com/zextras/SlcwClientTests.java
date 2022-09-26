package com.zextras;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.unboundid.ldap.sdk.*;
import com.zextras.slcwBeans.User;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
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
  public void shouldSuccessfullySearch() throws LDAPException {
    final SlcwClient client = new SlcwClient(embeddedLdapRule.unsharedLdapConnection());
    final List<SearchResultEntry> searchResult =
        client.search("dc=example,dc=com", SearchScope.SUB, "objectClass=person");
    assertEquals(3, searchResult.size());
    assertEquals(searchResult.get(0).getAttribute("cn").getValue(), "John Steinbeck");
    assertEquals(searchResult.get(1).getAttribute("cn").getValue(), "Micha Kops");
    assertEquals(searchResult.get(2).getAttribute("cn").getValue(), "Santa Claus");
  }

  @Test
  public void shouldGetById() throws LDAPException {
    final SlcwClient client = new SlcwClient(embeddedLdapRule.unsharedLdapConnection());
    final User expectedUser = new User("inetOrgPerson","Name", "Surname", 6785949);
    client.add(expectedUser);
    User actualUser = client.getById("Name Surname", User.class);
    assertEquals(expectedUser, actualUser);

  }
  @Test
  public void shouldReturnSuccessOnAddOperation() throws LDAPException {
    final SlcwClient client = new SlcwClient(embeddedLdapRule.unsharedLdapConnection());
    final User user = new User("inetOrgPerson","Name", "Surname", 6785949);
    final LDAPResult result = client.add(user);
    assertEquals(ResultCode.valueOf(0), result.getResultCode());
  }

  @Test
  public void shouldReturnSuccessOnDeleteOperation() throws LDAPException {
    final SlcwClient client = new SlcwClient(embeddedLdapRule.unsharedLdapConnection());
    final User user = new User("inetOrgPerson", "Name", "Surname", 6785949);

    client.add(user);
    List<SearchResultEntry> searchResult =
        client.search("dc=example,dc=com", SearchScope.SUB, "cn=Name Surname");
    assertEquals(1, searchResult.size());

    final LDAPResult result = client.delete(user);
    assertEquals(ResultCode.valueOf(0), result.getResultCode());
    searchResult = client.search("dc=example,dc=com", SearchScope.SUB, "cn=Name Surname");
    assertEquals(0, searchResult.size());
    assertEquals(ResultCode.valueOf(0), result.getResultCode());
  }

  @Test
  public void shouldReturnSuccessOnModifyOperation() throws LDAPException {
    final SlcwClient client = new SlcwClient(embeddedLdapRule.unsharedLdapConnection());
    final User user = new User("inetOrgPerson","Name", "Surname", 6785949);

    client.add(user);
    List<SearchResultEntry> searchResult =
        client.search("ou=users,dc=example,dc=com", SearchScope.ONE, "cn=Name Surname");
    assertEquals("6785949", searchResult.get(0).getAttribute("homePhone").getValue());

    user.setPhoneNumber(11111111);
    final LDAPResult result = client.update(user);
    assertEquals(ResultCode.valueOf(0), result.getResultCode());

    searchResult = client.search("ou=users,dc=example,dc=com", SearchScope.ONE, "cn=Name Surname");
    assertEquals("11111111", searchResult.get(0).getAttribute("homePhone").getValue());
  }
}
