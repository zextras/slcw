package com.zextras;

import com.unboundid.ldap.sdk.Filter;
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
 * Manages access in LDAP on extensions of {@link SlcwBean} through connections and operations.
 *
 * @author Yuliya Aheeva
 * @since 1.0.0
 */
public class SlcwClient<T extends SlcwBean> implements OperationExecutor<T> {

  private final LDAPConnectionPool ldapConnectionPool;
  private final Class<T> beanClazz;
  private final String baseDn;

  /**
   * @param ldapConnectionPool connection pool to LDAP
   * @param baseDn base dn for the object as parent dn
   * @param clazz bean class for objects we are going to manage
   */
  public SlcwClient(LDAPConnectionPool ldapConnectionPool, String baseDn, Class<T> clazz) {
    this.ldapConnectionPool = ldapConnectionPool;
    this.baseDn = baseDn;
    this.beanClazz = clazz;
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
  public OperationResult<T> add(T bean) {
    try {
      bean.setDn("uid=" + bean.getUid() + ", " + baseDn);
      LDAPResult result = ldapConnectionPool.add(bean.getDn(), SlcwConverter.convertFieldsToAttributes(bean));
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), new ArrayList<T>());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  @Override
  public OperationResult<T> update(T bean) {
    try {
      LDAPResult result = ldapConnectionPool.modify(bean.getDn(), SlcwConverter.convertFieldsToModifications(bean));
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), new ArrayList<>());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  @Override
  public OperationResult<T> delete(T bean) {
    try {
      LDAPResult result = ldapConnectionPool.delete(bean.getDn());
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), new ArrayList<>());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  @Override
  public OperationResult<T> search(String baseDn, String filter) {
    try {
      SearchRequest searchRequest = new SearchRequest(baseDn, SearchScope.ONE, filter);
      final SearchResult result = ldapConnectionPool.search(searchRequest);
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), SlcwConverter.convertEntriesToBeans(result.getSearchEntries(), beanClazz));
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  @Override
  public OperationResult<T> search(String uid) {
    return this.search(baseDn, Filter.createEqualityFilter("uid", uid).toNormalizedString());
  }

}
