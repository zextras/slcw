package com.zextras;

import com.unboundid.ldap.sdk.Filter;
import com.unboundid.ldap.sdk.LDAPConnectionPool;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchScope;
import com.zextras.operations.executors.OperationExecutor;
import com.zextras.operations.results.OperationResult;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.converters.SlcwConverter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Manages LDAP operations for a {@link SlcwBean} type.
 *
 * @author Yuliya Aheeva
 * @since 1.0.0
 */
public class SlcwClient<T extends SlcwBean> implements OperationExecutor<T> {

  private final LDAPConnectionPool ldapConnectionPool;
  private final Class<T> beanClazz;
  private final String baseDn;

  /**
   *
   * @param ldapConnectionPool connection pool to LDAP
   * @param baseDn base dn for the object as parent dn
   * @param clazz bean class for objects we are going to manage
   */
  public SlcwClient(LDAPConnectionPool ldapConnectionPool, String baseDn, Class<T> clazz) {
    this.ldapConnectionPool = ldapConnectionPool;
    this.baseDn = baseDn;
    this.beanClazz = clazz;
  }

  /**
   * Returns basedDn if bean does not have an id or id has no value, else id + baseDn.
   *
   * @param bean managed object
   * @return full dn
   */
  private String getFullDn(T bean) {
    final String idValue = bean.getIdStringValue();
    if (bean.hasIdAttribute()) {
      if ((!Objects.equals(idValue, ""))) {
        return bean.getIdAttrName() + "=" + idValue + ", " + baseDn;
      }
    }
    return baseDn;
  }



  @Override
  public OperationResult<T> add(T bean) {
    try {
      bean.setDn(getFullDn(bean));
      LDAPResult result = ldapConnectionPool.add(bean.getDn(), SlcwConverter.convertFieldsToAttributes(bean));
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), new ArrayList<T>());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    } catch (Exception e) {
      throw new SlcwException(e.getMessage());
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

  /**
   * Searches specific object if object has an id, else matches all entries directly underneath baseDn.
   *
   * @param bean managed object
   * @return
   */
  @Override
  public OperationResult<T> search(T bean) {
    try {
      SearchScope searchScope;
      Filter filter;
      if (Objects.equals("", bean.getIdStringValue())) {
        searchScope = SearchScope.ONE;
        //TODO: this is broken, create a filter based on all other fields
        filter = Filter.createApproximateMatchFilter(bean.getIdAttrName(), bean.getIdStringValue());
      } else {
        searchScope = SearchScope.BASE;
        filter = Filter.createEqualityFilter(bean.getIdAttrName(), bean.getIdStringValue());
      }
      SearchRequest searchRequest = new SearchRequest(getFullDn(bean), searchScope, filter);
      final SearchResult result = ldapConnectionPool.search(searchRequest);
      return new OperationResult<T>(result.getResultCode().getName(),
          result.getResultCode().intValue(), SlcwConverter.convertEntriesToBeans(result.getSearchEntries(), beanClazz));
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    } catch (Exception e) {
      throw new SlcwException(e.getMessage());
    }
  }

}
