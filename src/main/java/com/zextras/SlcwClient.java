package com.zextras;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPConnectionPool;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchScope;
import com.zextras.operations.executors.OperationExecutor;
import com.zextras.operations.results.OperationResult;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.converters.SlcwConverter;
import java.util.ArrayList;

/**
 * Manages access to LDAP thorugh connections and operations.
 *
 * @author Yuliya Aheeva
 * @since 1.0.0
 */
public class SlcwClient implements OperationExecutor {

  private final LDAPConnectionPool ldapConnectionPool;
  private final String baseDn;

  public SlcwClient(LDAPConnectionPool ldapConnectionPool, String baseDn) {
    this.ldapConnectionPool = ldapConnectionPool;
    this.baseDn = baseDn;
  }

  /**
   * Authenticate a user and open the LDAP client connection, otherwise throws an exception.
   *
   * @param host     a network layer host address.
   * @param port     a port on a host that connects it to the storage system.
   * @param bindDN   a Username used to connect to the server.
   * @param password a secret word or phrase that allows access to the server.
   * @param baseDn   the starting point on the server.
   */
  public static SlcwClient initialize(String host, int port, String bindDN, String password, String baseDn, Integer numConnections) throws LDAPException{
    return new SlcwClient(new LDAPConnectionPool(new LDAPConnection(host, port, bindDN, password), numConnections), baseDn);
  }

  /**
   * @param ldapConnection an LDAP connection
   * @param baseDn base dn to start from when executing operations
   * @param numConnections number of connections
   * @return
   * @throws LDAPException
   */
  public static SlcwClient initialize(LDAPConnection ldapConnection, String baseDn, Integer numConnections) throws LDAPException{
    return new SlcwClient(new LDAPConnectionPool(ldapConnection, numConnections), baseDn);
  }

  private SearchResult search(String baseDN, SearchScope searchScope, String filter) {
    SearchResult searchResult;
    try {
      searchResult = ldapConnectionPool.search(baseDN, searchScope, filter);
    } catch (LDAPSearchException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
    return searchResult;
  }

  @Override
  public <T extends SlcwBean> OperationResult<T> add(T bean) {
    try {
      LDAPResult result = ldapConnectionPool.add(bean.getDn(), SlcwConverter.convertFieldsToAttributes(bean));
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), new ArrayList<T>());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  @Override
  public <T extends SlcwBean> OperationResult<T> update(T bean) {
    try {
      LDAPResult result = ldapConnectionPool.modify(bean.getDn(), SlcwConverter.convertFieldsToModifications(bean));
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), new ArrayList<>());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  @Override
  public <T extends SlcwBean> OperationResult<T> delete(T bean) {
    try {
      LDAPResult result = ldapConnectionPool.delete(bean.getDn());
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), new ArrayList<>());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  @Override
  public <T extends SlcwBean> OperationResult<T> search(String baseDn, String filter) {
    try {
      SearchRequest searchRequest = new SearchRequest(baseDn, SearchScope.BASE, filter);
      final SearchResult result = ldapConnectionPool.search(searchRequest);
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), new ArrayList<>());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

}
