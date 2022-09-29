package com.zextras;

import com.unboundid.ldap.sdk.*;
import com.zextras.operations.ldap.LdapOperationExecutor;
import com.zextras.operations.OperationResult;
import com.zextras.persistence.converters.SlcwConverter;
import com.zextras.persistence.mapping.SlcwEntry;
import com.zextras.persistence.mapping.SlcwMapper;
import com.zextras.utils.PropertyBuilder;
import com.zextras.utils.ObjectFactory;

/**
 * Main entrypoint for the library.
 */
public class SlcwClient {

  private LDAPConnection connection;
  private final String baseDn;
  private final SlcwMapper mapper = new SlcwMapper();
  private final PropertyBuilder builder = new PropertyBuilder();

  /**
   * Creates a client with an opened connection.*
   *
   * @param connection an opened connection to the server.
   * @param baseDn     the starting point on the server.
   */
  public SlcwClient(LDAPConnection connection, String baseDn) {
    this.baseDn = baseDn;
    this.connection = connection;
  }

  /**
   * Creates a client, authenticate a user and change the identity of the client connection.*
   *
   * @param host     a network layer host address.
   * @param port     a port on a host that connects it to the storage system.
   * @param bindDN   a Username used to connect to the server.
   * @param password a secret word or phrase that allows access to the server.
   * @param baseDn   the starting point on the server.
   */
  public SlcwClient(String host, int port, String bindDN, String password, String baseDn) {
    this.baseDn = baseDn;
    initialize(host, port, bindDN, password);
  }

  private void initialize(String host, int port, String bindDN, String password) {
    try {
      connection = new LDAPConnection(host, port, bindDN, password);
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets an object from an entry (record) stored in the structure, otherwise throws an exception.*
   *
   * @param id    a unique identifier that marks that particular record as unique from every other
   *              record.
   * @param clazz type of the class which represents an object that you wanted to get. (ex.
   *              User.class)
   * @param <T>   is a conventional letter that stands for "Type".
   * @return an object of the given class.
   */
  public <T> T getById(String id, Class<T> clazz) {
    T object = ObjectFactory.newObject(clazz);
    SlcwEntry entry = new SlcwEntry(baseDn);

    mapper.map(object, entry);

    entry.setDn(builder.buildDn(entry, object));
    entry.setFilter(builder.buildFilter(entry));
    entry.setSearchScope(SearchScope.ONE);

    LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    executor.executeGetOperation(entry);

    mapper.map(entry, object);
    return object;
  }

  /**
   * Creates a new entry in the structure.*
   *
   * @param object an object that you want to save in the structure.
   * @param <T>    is a conventional letter that stands for "Type".
   * @return a result of an adding operation. (ex. "0 (success)").
   */
  public <T> OperationResult add(T object) {
    SlcwEntry entry = new SlcwEntry(baseDn);
    mapper.map(object, entry);
    entry.setFilter(builder.buildFilter(entry));
    entry.setDn(builder.buildDn(entry, object));

    entry.setAttributes(SlcwConverter.convertFieldsToAttributes(entry));

    LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    return executor.executeAddOperation(entry);
  }

  /**
   * Alter the content of an entry in the structure.*
   *
   * @param object an object that you want to modify in the structure.
   * @param <T>    is a conventional letter that stands for "Type".
   * @return a result of an adding operation. (ex. "0 (success)").
   */
  public <T> OperationResult update(T object) {
    SlcwEntry entry = new SlcwEntry(baseDn);

    mapper.map(object, entry);
    builder.buildFilter(entry);

    entry.setAttributes(SlcwConverter.convertFieldsToModifications(entry));

    LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    return executor.executeUpdateOperation(entry);
  }

  /**
   * Remove an entry from the structure.*
   *
   * @param object an object that you want to remove.
   * @param <T>    is a conventional letter that stands for "Type".
   * @return a result of an adding operation. (ex. "0 (success)").
   */
  public <T> OperationResult delete(T object) { //todo change for id
    SlcwEntry entry = new SlcwEntry(baseDn);

    mapper.map(object, entry);
    builder.buildFilter(entry);

    LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    return executor.executeDeleteOperation(entry);
  }
}
