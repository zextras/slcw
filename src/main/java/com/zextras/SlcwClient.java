package com.zextras;

import com.unboundid.ldap.sdk.*;
import com.zextras.operations.executors.LdapOperationExecutor;
import com.zextras.operations.results.LdapOperationResult;
import com.zextras.operations.results.OperationResult;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.converters.SlcwConverter;
import com.zextras.persistence.mapping.entries.SlcwEntry;
import com.zextras.persistence.mapping.mappers.SlcwMapper;
import com.zextras.utils.ObjectFactory;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main entrypoint for the library.
 *
 * @author Yuliya Aheeva
 * @since 1.0.0
 */
public class SlcwClient {

  private final LDAPConnection connection;
  private final String baseDn;
  private final SlcwMapper mapper = new SlcwMapper();

  /**
   * Creates a client with an opened connection.
   *
   * @param connection an opened connection to the server.
   * @param baseDn     the starting point on the server.
   */
  public SlcwClient(LDAPConnection connection, String baseDn) {
    this.baseDn = baseDn;
    this.connection = connection;
  }

  /**
   * Authenticate a user and open the client connection, otherwise throws an exception.
   *
   * @param host     a network layer host address.
   * @param port     a port on a host that connects it to the storage system.
   * @param bindDN   a Username used to connect to the server.
   * @param password a secret word or phrase that allows access to the server.
   * @param baseDn   the starting point on the server.
   */
  public static SlcwClient initialize(String host, int port, String bindDN, String password, String baseDn) {
    try {
      return new SlcwClient(new LDAPConnection(host, port, bindDN, password), baseDn);
    } catch (LDAPException e) {
      throw new SlcwException(e.getExceptionMessage());
    }
  }

  /**
   * Gets an object from a record stored in the structure, otherwise throws an exception.
   *
   * @param id    a unique identifier that marks that particular record as unique from every other
   *              record.
   * @param clazz type of the class which represents an object that you wanted to get. (ex.
   *              User.class)
   * @param <T>   is a conventional letter that stands for "Type".
   * @return an object of the given class.
   */
  public <T> T getById(String id, Class<T> clazz) {
    SlcwEntry entry = new SlcwEntry(baseDn);
    entry.getId().setPropertyValue(id);

    LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    LdapOperationResult result = executor.executeGetOperation(entry);
    entry.setAttributes(result.getData());
    return mapper.map(entry, clazz);
  }

  /**
   * Creates a new entry (record) in the structure.*
   *
   * @param object an object that you want to save in the structure.
   * @param <T>    is a conventional letter that stands for "Type".
   * @return a result of an adding operation. (ex. "0 (success)").
   */
  public <T> OperationResult add(T object) {
    SlcwEntry entry = mapper.map(object);
    entry.setDn(baseDn);
    entry.setAttributes(SlcwConverter.convertFieldsToAttributes(entry));

    LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    return executor.executeAddOperation(entry);
  }

  /**
   * Alter the content of an entry (record) in the structure.
   *
   * @param object an object that you want to modify in the structure.
   * @param <T>    is a conventional letter that stands for "Type".
   * @return a result of an adding operation. (ex. "0 (success)").
   */
  public <T> OperationResult update(T object) {
    SlcwEntry entry = mapper.map(object);
    entry.setDn(baseDn);
    entry.setAttributes(SlcwConverter.convertFieldsToModifications(entry));

    LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    return executor.executeUpdateOperation(entry);
  }

  /**
   * Remove an entry (record) from the structure.
   *
   * @param object an object that you want to delete from the structure.
   * @param <T>    is a conventional letter that stands for "Type".
   * @return a result of an adding operation. (ex. "0 (success)").
   */
  public <T> OperationResult delete(T object) {
    SlcwEntry entry = mapper.map(object);
    entry.setDn(baseDn);

    LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    return executor.executeDeleteOperation(entry);
  }

  public <T> List<T> search(Class<T> clazz) {
    SlcwEntry entry = new SlcwEntry(baseDn);

    LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    return executor.executeGetAllOperation(entry).getData().stream().map(
        attributes -> mapper.map(new SlcwEntry(baseDn, attributes), clazz)
    ).collect(Collectors.toList());
  }

}
