package com.zextras.operations.results;

import com.unboundid.ldap.sdk.Attribute;
import com.zextras.persistence.mapping.entries.SlcwEntry;
import java.util.Collection;
import java.util.Set;

/**
 * Class that provides information about the result specific for Ldap operations you perform.
 */
public class LdapOperationResult extends OperationResult {

  public Set<Collection<Attribute>> getData() {
    return data;
  }

  private Set<Collection<Attribute>> data;

  /**
   * Creates a Ldap operation result with code value and message information.
   *
   * @param name     plain message of an operation which is clear to you.
   * @param intValue plain operation code which is clear to you.
   */
  public LdapOperationResult(String name, int intValue) {
    super(name, intValue);
  }

  /**
   * Creates a Ldap operation result with code value, message information and attributes of an
   * object you were searching for with
   * {@link com.zextras.operations.executors.LdapOperationExecutor#executeGetOperation(SlcwEntry)
   * executeGetOperation} method.
   *
   * @param name       plain message of an operation which is clear to you.
   * @param intValue   plain operation code which is clear to you.
   * @param data attributes of an object you were searching for.
   */
  public LdapOperationResult(String name, int intValue, Set<Collection<Attribute>> data) {
    super(name, intValue);
    this.data = data;
  }
}
