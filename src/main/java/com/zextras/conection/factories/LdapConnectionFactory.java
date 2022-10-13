package com.zextras.conection.factories;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.zextras.conection.configs.LdapConnectionConfig;
import com.zextras.persistence.SlcwException;

/**
 * A class is used to provide a {@link com.zextras.SlcwClient} client with an LDAP connection.
 */
public class LdapConnectionFactory extends AbstractConnectionFactory<LDAPConnection> {

  private final LdapConnectionConfig config;

  public LdapConnectionFactory(final LdapConnectionConfig config) {
    this.config = config;
  }

  /**
   * Authenticate a user and open the LDAP connection, otherwise throws an exception.
   *
   * @return opened connection.
   */
  @Override
  public LDAPConnection openConnection() {
    if (config == null) {
      throw new SlcwException("Config class is null.");
    }
    try {
      return new LDAPConnection(
          config.getHost(),
          config.getPort(),
          config.getBindDn(),
          config.getPassword());
    } catch (final LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }
}
