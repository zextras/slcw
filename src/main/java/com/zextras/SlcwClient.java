package com.zextras;

import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.zextras.operations.executors.LdapOperationExecutor;
import com.zextras.operations.executors.OperationExecutor;
import com.zextras.operations.results.OperationResult;
import com.zextras.persistence.SlcwException;
import java.util.List;

/**
 * Main entrypoint for the library.
 *
 * @author Yuliya Aheeva
 * @since 1.0.0
 */
public class SlcwClient {

  private final OperationExecutor operationExecutor;
  private final String baseDn;

  /**
   * Creates a client with an opened connection.
   *
   * @param operationExecutor an operation executor
   * @param baseDn     the starting point on the server.
   */
  public SlcwClient(OperationExecutor operationExecutor, String baseDn) {
    this.baseDn = baseDn;
    this.operationExecutor = operationExecutor;
  }

  public SlcwClient(LDAPConnection ldapConnection, String baseDn) {
    this(new LdapOperationExecutor(ldapConnection), baseDn);
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
  public static SlcwClient initialize(String host, int port, String bindDN, String password, String baseDn) {
    try {
      return new SlcwClient(new LdapOperationExecutor(new LDAPConnection(host, port, bindDN, password)), baseDn);
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  /**
   * Gets an object from a record stored in the structure, otherwise throws an exception.
   *
   * @param filter a generic filter.
   * @param <T>   is a conventional letter that stands for "Type".
   * @return an object of the given class.
   */
  public <T  extends SlcwBean> List<T> search(String filter, Class<T> clazz) {
    final OperationResult<T> slcwBeanOperationResult = operationExecutor.executeSearchOperation(
        baseDn, filter);
    return slcwBeanOperationResult.getData();
  }

  /**
   * Creates a new entry (record) in the structure.*
   *
   * @param object an object that you want to save in the structure.
   * @param <T>    is a conventional letter that stands for "Type".
   * @return a result of an adding operation. (ex. "0 (success)").
   */
  public <T extends SlcwBean> OperationResult<T> add(T object) {
    return operationExecutor.executeAddOperation(object);
  }

  /**
   * Alter the content of an entry (record) in the structure.
   *
   * @param object an object that you want to modify in the structure.
   * @param <T>    is a conventional letter that stands for "Type".
   * @return a result of an adding operation. (ex. "0 (success)").
   */
  public <T extends SlcwBean> OperationResult<T> update(T object) {
    return operationExecutor.executeUpdateOperation(object);
  }

  /**
   * Remove an entry (record) from the structure.
   *
   * @param object an object that you want to delete from the structure.
   * @param <T>    is a conventional letter that stands for "Type".
   * @return a result of an adding operation. (ex. "0 (success)").
   */
  public <T extends SlcwBean> OperationResult<T> delete(T object) {
    return operationExecutor.executeDeleteOperation(object);
  }

}
