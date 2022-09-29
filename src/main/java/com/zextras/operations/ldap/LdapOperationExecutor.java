package com.zextras.operations.ldap;

import com.unboundid.ldap.sdk.*;
import com.zextras.operations.AbstractOperationExecutor;
import com.zextras.operations.OperationResult;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.mapping.SlcwEntry;

import java.util.Collection;
import java.util.List;

/**
 * Helper class that interacts with LDAP server and performs CRUD operations.
 */
public class LdapOperationExecutor extends AbstractOperationExecutor {

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

  /**
   * Performs an add operation and returns the result of this operation.
   *
   * @param entry a representation of an object and a future corresponding record in the structure.
   * @return a result of the operation.
   */
  public OperationResult executeAddOperation(SlcwEntry entry) {
    Collection<Attribute> attributes = (Collection<Attribute>) entry.getAttributes();
    try {
      LDAPResult result = connection.add(entry.getFilter() + "," + entry.getDn(), attributes);
      return new OperationResult(result.getResultCode().getName(),
          result.getResultCode().intValue());
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Performs an update operation and returns the result of this operation.
   *
   * @param entry a representation of an object and a future corresponding record in the structure.
   * @return a result of the operation.
   */
  public OperationResult executeUpdateOperation(SlcwEntry entry) {
    List<Modification> modifications = (List<Modification>) entry.getAttributes();
    try {
      LDAPResult result = connection.modify(entry.getFilter() + "," + entry.getDn(), modifications);
      return new OperationResult(result.getResultCode().getName(),
          result.getResultCode().intValue());
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Performs a removal operation and returns the result of this operation.
   *
   * @param entry a representation of an object and a future corresponding record in the structure.
   * @return a result of the operation.
   */
  public OperationResult executeDeleteOperation(SlcwEntry entry) {
    try {
      LDAPResult result = connection.delete(entry.getFilter() + "," + entry.getDn());
      return new OperationResult(result.getResultCode().getName(),
          result.getResultCode().intValue());
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Performs a get by id operation and returns the result of this operation.
   *
   * @param entry a representation of an object and a future corresponding record in the structure.
   * @return a result of the operation.
   */
  public OperationResult executeGetOperation(SlcwEntry entry) {
    var result = search(entry.getDn(), entry.getSearchScope(), entry.getFilter());
    var searchResultEntries = result.getSearchEntries();
    if (searchResultEntries.isEmpty()) {
      throw new SlcwException(String.format("Object %s not found.", entry.getId().getFiledValue()));
    }

    entry.setAttributes(searchResultEntries.get(0).getAttributes());
    return new OperationResult(result.getResultCode().getName(), result.getResultCode().intValue());
  }

  private SearchResult search(String baseDN, SearchScope searchScope, String filter) {
    SearchResult searchResult;
    try {
      searchResult = connection.search(baseDN, searchScope, filter);
    } catch (LDAPSearchException e) {
      throw new RuntimeException(e);
    }
    return searchResult;
  }
}
