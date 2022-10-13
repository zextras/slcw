package com.zextras.operations.results;

import com.unboundid.ldap.sdk.SearchResultEntry;
import com.zextras.persistence.mapping.entries.SlcwEntry;
import java.util.List;

/**
 * Class that provides information about the result specific for Ldap operations you perform.
 */
public class LdapOperationResult extends OperationResult {

  private long entriesReturned;
  private List<SearchResultEntry> entries;

  /**
   * Creates a Ldap operation result with code value and message information.
   *
   * @param name     plain message of an operation which is clear to you.
   * @param intValue plain operation code which is clear to you.
   */
  public LdapOperationResult(final String name, final int intValue) {
    super(name, intValue);
  }

  /**
   * Creates a Ldap operation result with code value, message information and attributes of an
   * object you were searching for with
   * {@link com.zextras.operations.executors.LdapOperationExecutor#executeGetOperation(SlcwEntry)
   * executeCountOperation} method.
   *
   * @param name     plain message of an operation which is clear to you.
   * @param intValue plain operation code which is clear to you.
   * @param entries  search operation matching entries.
   */

  public LdapOperationResult(final String name, final int intValue,
      final List<SearchResultEntry> entries) {
    super(name, intValue);
    this.entries = entries;
  }

  public LdapOperationResult(final String name, final int intValue, final long entriesReturned) {
    super(name, intValue);
    this.entriesReturned = entriesReturned;
  }

  public LdapOperationResult(final String name, final int intValue, final long entriesReturned,
      final List<SearchResultEntry> entries) {
    super(name, intValue);
    this.entriesReturned = entriesReturned;
    this.entries = entries;
  }

  public List<SearchResultEntry> getEntries() {
    return entries;
  }

  public void setEntries(final List<SearchResultEntry> entries) {
    this.entries = entries;
  }

  public long getEntriesReturned() {
    return entriesReturned;
  }

  public void setEntriesReturned(final long entriesReturned) {
    this.entriesReturned = entriesReturned;
  }
}
