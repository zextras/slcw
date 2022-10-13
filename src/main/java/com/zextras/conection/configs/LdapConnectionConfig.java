package com.zextras.conection.configs;

import com.zextras.conection.Password;
import com.zextras.persistence.Filter;

/**
 * LdapConnectionConfig contains all the information needed to open an LDAP connection. The
 * information should be provided using {@link #builder() builder} method.
 */
public class LdapConnectionConfig extends AbstractConfig {

  private String host;
  private int port;
  private String bindDn;
  private Password password;

  /**
   * Private constructor should only be used by {@link Builder} class.
   */
  private LdapConnectionConfig() {

  }

  /**
   * Creates a builder class in order to set private fields of the class.
   *
   * @return {@link Builder} class.
   */
  public static Builder builder() {
    return new Builder();
  }

  private void setHost(final String host) {
    this.host = host;
  }

  private void setPort(final int port) {
    this.port = port;
  }

  private void setBindDn(final String bindDn) {
    this.bindDn = bindDn;
  }

  private void setPassword(final Password password) {
    this.password = password;
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public String getPassword() {
    return password.getString();
  }

  public String getBindDn() {
    return bindDn;
  }

  /**
   * Builder class is used to instantiate {@link LdapConnectionConfig} class.
   */
  public static class Builder {

    private final LdapConnectionConfig object = new LdapConnectionConfig();

    protected Builder() {
    }

    /**
     * Sets a host of a config class in order to open connection.
     *
     * @param host a network layer host address.
     * @return {@link Builder}.
     */
    public Builder host(final String host) {
      this.object.setHost(host);
      return this;
    }

    /**
     * Sets a port of a config class in order to open connection.
     *
     * @param port a port on a host that connects it to the storage system.
     * @return {@link Builder}.
     */
    public Builder port(final int port) {
      this.object.setPort(port);
      return this;
    }

    /**
     * Sets a bindDN of a config class in order to open connection.
     *
     * @param bindDN a Username used to connect to the server.
     * @return {@link Builder}.
     */
    public Builder bindDN(final String bindDN) {
      this.object.setBindDn(bindDN);
      return this;
    }

    /**
     * Sets a string password of a config class in order to open connection.
     *
     * @param password a secret word or phrase that allows access to the server.
     * @return {@link Builder}.
     */
    public Builder password(final String password) {
      this.object.setPassword(new Password(password));
      return this;
    }

    /**
     * Sets a byte array password of a config class in order to open connection.
     *
     * @param password a secret word or phrase that allows access to the server.
     * @return {@link Builder}.
     */
    public Builder password(final byte[] password) {
      this.object.setPassword(new Password(password));
      return this;
    }

    /**
     * Completes building a config.
     *
     * @return built {@link Filter}.
     */
    public LdapConnectionConfig build() {
      return this.object;
    }
  }
}
