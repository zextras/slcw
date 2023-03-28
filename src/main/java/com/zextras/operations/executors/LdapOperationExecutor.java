package com.zextras.operations.executors;

import com.unboundid.ldap.sdk.*;
import com.zextras.operations.results.LdapOperationResult;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.mapping.entries.SlcwEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class that interacts with LDAP server and performs CRUD operations.
 */
public class LdapOperationExecutor extends AbstractOperationExecutor<SlcwEntry> {

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
  public LdapOperationResult executeAddOperation(SlcwEntry entry) {
    Collection<Attribute> attributes = (Collection<Attribute>) entry.getAttributes();
    try {
      LDAPResult result = connection.add(entry.getFilter() + "," + entry.getDn(), attributes);
      return new LdapOperationResult(result.getResultCode().getName(),
          result.getResultCode().intValue());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  /**
   * Performs an update operation and returns the result of this operation.
   *
   * @param entry a representation of an object and a future corresponding record in the structure.
   * @return a result of the operation.
   */
  public LdapOperationResult executeUpdateOperation(SlcwEntry entry) {
    List<Modification> modifications = (List<Modification>) entry.getAttributes();
    try {
      LDAPResult result = connection.modify(entry.getFilter() + "," + entry.getDn(), modifications);
      return new LdapOperationResult(result.getResultCode().getName(),
          result.getResultCode().intValue());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  /**
   * Performs a removal operation and returns the result of this operation.
   *
   * @param entry a representation of an object and a future corresponding record in the structure.
   * @return a result of the operation.
   */
  public LdapOperationResult executeDeleteOperation(SlcwEntry entry) {
    try {
      LDAPResult result = connection.delete(entry.getFilter() + "," + entry.getDn());
      return new LdapOperationResult(result.getResultCode().getName(),
          result.getResultCode().intValue());
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  /**
   * Performs a get by id operation and returns the result of this operation.
   *
   * @param entry a representation of an object and a future corresponding record in the structure.
   * @return a result of the operation.
   */
  public LdapOperationResult executeGetOperation(SlcwEntry entry) {
    var result = search(entry.getDn(), SearchScope.ONE, entry.getFilter());
    var searchResultEntries = result.getSearchEntries();
    return new LdapOperationResult(result.getResultCode().getName(),
        result.getResultCode().intValue(),
        Collections.singleton(searchResultEntries.get(0).getAttributes()));
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

  public LdapOperationResult executeGetAllOperation(SlcwEntry entry) {
    var result = search(entry.getDn(), SearchScope.BASE, entry.getFilter());
    var searchResultEntries = result.getSearchEntries();
    if (searchResultEntries.isEmpty()) {
      throw new SlcwException(
          String.format("Object %s not found.", entry.getId().getPropertyValue()));
    }
    return new LdapOperationResult(result.getResultCode().getName(),
        result.getResultCode().intValue(),
        searchResultEntries.stream().map(SearchResultEntry::getAttributes).collect(Collectors.toSet()));
  }


}
