package com.zextras.operations.executors;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchScope;
import com.zextras.SlcwBean;
import com.zextras.operations.results.OperationResult;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.converters.SlcwConverter;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class that interacts with LDAP server and performs CRUD operations.
 */
public class LdapOperationExecutor implements OperationExecutor {

  private final LDAPConnection connection;

  /**
   * Creates an executor with an opened LDAP connection by which it would interact with LDAP
   * server.
   *
   * @param connection an opened LDAP connection by which executor would interact with LDAP server.
   */
  public LdapOperationExecutor(LDAPConnection connection) {
    this.connection = connection;
  }

  private SearchResult search(String baseDN, SearchScope searchScope, String filter) {
    SearchResult searchResult;
    try {
      searchResult = connection.search(baseDN, searchScope, filter);
    } catch (LDAPSearchException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
    return searchResult;
  }

  @Override
  public <T extends SlcwBean> OperationResult<T> executeAddOperation(T bean) {
    try {
      LDAPResult result = connection.add(bean.getDn(), SlcwConverter.convertFieldsToAttributes(bean));
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), new ArrayList<T>());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  @Override
  public <T extends SlcwBean> OperationResult<T> executeUpdateOperation(T bean) {
    try {
      LDAPResult result = connection.modify(bean.getDn(), SlcwConverter.convertFieldsToModifications(bean));
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), new ArrayList<>());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  @Override
  public <T extends SlcwBean> OperationResult<T> executeDeleteOperation(T bean) {
    try {
      LDAPResult result = connection.delete(bean.getDn());
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), new ArrayList<>());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  @Override
  public <T extends SlcwBean> OperationResult<T> executeSearchOperation(String baseDn, String filter) {
    try {
      LDAPResult result = connection.search(baseDn, SearchScope.BASE, filter);
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), new ArrayList<>());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }
}
