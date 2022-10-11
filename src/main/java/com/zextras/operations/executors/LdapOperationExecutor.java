package com.zextras.operations.executors;

import com.unboundid.asn1.ASN1OctetString;
import com.unboundid.ldap.sdk.*;
import com.unboundid.ldap.sdk.controls.SimplePagedResultsControl;
import com.unboundid.util.LDAPTestUtils;
import com.zextras.operations.results.LdapOperationResult;
import com.zextras.persistence.Filter;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.mapping.entries.SlcwEntry;

import java.util.Collection;
import java.util.List;

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
      LDAPResult result = connection.add(entry.getFilter().getFilter() + "," + entry.getDn(),
          attributes);
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
      LDAPResult result = connection.modify(entry.getFilter().getFilter() + "," + entry.getDn(),
          modifications);
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
      LDAPResult result = connection.delete(entry.getFilter().getFilter() + "," + entry.getDn());
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
    var result = search(entry.getDn(), SearchScope.ONE, entry.getFilter().getFilter());
    var searchResultEntries = result.getSearchEntries();
    if (searchResultEntries.isEmpty()) {
      throw new SlcwException(
          String.format("Object %s not found.", entry.getId().getPropertyValue()));
    }
    return new LdapOperationResult(result.getResultCode().getName(),
        result.getResultCode().intValue(), searchResultEntries);
  }

  /**
   * Counts a number of entries which match a given filter.
   *
   * @param filter {@link Filter}.
   * @return a number of matching entries.
   * @see #search(Filter).
   */
  public LdapOperationResult executeCountOperation(Filter filter) {
    var result = search(filter);
    return new LdapOperationResult("Success", 0, result);
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


  /**
   * Applies pagedResultsControl to the search operation. It allows the client to iterate through a
   * potentially large set of search results in subsets of a specified number of entries (i.e.,
   * "pages"). Setting a page size allows the server to send the data in pages as the pages are
   * being built. The client then caches this data and provides a cursor to the application level
   * code while another thread concurrently receives more data from the server. Paging is set by
   * defining how many rows the server calculates before the data is returned over the network to
   * the client.
   * <p>
   * If the client does not want any attributes returned, it can specify a list containing only the
   * attribute with OID "1.1". This OID was chosen arbitrarily and does not correspond to any
   * attribute in use.
   * <p>
   * !NB! Some servers don't support the simple paged results control, so it is recommended to
   * contact your directory administrator to determine if the option is available.
   */

  private long search(Filter filter) {
    long numSearches = 0;
    long totalEntriesReturned = 0;
    SearchRequest searchRequest;
    try {
      searchRequest = new SearchRequest(filter.getDn(), filter.getSearchScope(),
          filter.getFilter(), "1.1");
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }

    ASN1OctetString resumeCookie = null;
    while (true) {
      searchRequest.setControls(
          new SimplePagedResultsControl(500, resumeCookie));
      SearchResult searchResult;
      try {
        searchResult = connection.search(searchRequest);
      } catch (LDAPSearchException e) {
        throw new SlcwException(e.getExceptionMessage());
      }
      numSearches++;
      totalEntriesReturned += searchResult.getEntryCount();

      LDAPTestUtils.assertHasControl(searchResult,
          SimplePagedResultsControl.PAGED_RESULTS_OID);

      SimplePagedResultsControl responseControl;
      try {
        responseControl = SimplePagedResultsControl.get(searchResult);
      } catch (LDAPException e) {
        throw new SlcwException(e.getExceptionMessage());
      }
      if (responseControl.moreResultsToReturn()) {
        // The resume cookie can be included in the simple paged results
        // control included in the next search to get the next page of results.
        resumeCookie = responseControl.getCookie();
      } else {
        break;
      }
    }
    return totalEntriesReturned;
  }
}
