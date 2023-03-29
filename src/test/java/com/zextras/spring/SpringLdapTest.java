package com.zextras.spring;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.DN;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPConnectionPool;
import com.unboundid.ldap.sdk.LDAPException;
import com.zextras.SlcwClient;
import com.zextras.slcwBeans.User;
import java.util.Hashtable;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

public class SpringLdapTest {


  private static final String BASE_DN = "dc=example, dc=com";
  private static final Integer PORT = 3000;
  private static final String USER_DN = "cn=test, " + BASE_DN;
  private static final String PASSWORD = "password";
  private static LdapTemplate ldapTemplate;
  private static final LdapContextSource contextSource = new LdapContextSource();

  @BeforeClass
  public static void setUp() throws Exception {
    InMemoryListenerConfig listenerConfig = InMemoryListenerConfig.createLDAPConfig("default", PORT);
    InMemoryDirectoryServerConfig serverConfig = new InMemoryDirectoryServerConfig(new DN(BASE_DN));
    serverConfig.setListenerConfigs(listenerConfig);
    InMemoryDirectoryServer server = new InMemoryDirectoryServer(serverConfig);
    server.importFromLDIF(true, "src/test/resources/in-memory-server.ldif");
    server.startListening();
    serverConfig.addAdditionalBindCredentials(USER_DN, PASSWORD);
    serverConfig.setSchema(null);
    contextSource.setUserDn(USER_DN);
    contextSource.setPassword(PASSWORD);
    contextSource.setBase(BASE_DN);
    contextSource.setUrl("ldap://127.0.0.1:" + Integer.toString(PORT));
    contextSource.afterPropertiesSet(); // must be called to initialize everything
    ldapTemplate  = new LdapTemplate(contextSource);
  }

  @Test
  public void shouldCreateUser() {
    final SpringUser springUser = new SpringUser();
    springUser.setName("name");
    springUser.setSurname("surname");
    ldapTemplate.create(springUser);
  }

}
