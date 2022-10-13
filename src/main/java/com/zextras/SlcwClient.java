package com.zextras;

import com.unboundid.ldap.sdk.*;
import com.zextras.conection.factories.LdapConnectionFactory;
import com.zextras.operations.executors.LdapOperationExecutor;
import com.zextras.operations.results.LdapOperationResult;
import com.zextras.operations.results.OperationResult;
import com.zextras.persistence.Filter;
import com.zextras.persistence.converters.SlcwConverter;
import com.zextras.persistence.mapping.entries.SlcwEntry;
import com.zextras.persistence.mapping.mappers.SlcwMapper;
import com.zextras.utils.ObjectFactory;
import java.util.Objects;

/**
 * Main entrypoint for the library.
 *
 * @author Yuliya Aheeva
 * @since 1.0.0
 */
public class SlcwClient {

  private LDAPConnection connection;
  private String baseDn;
  private final SlcwMapper mapper = new SlcwMapper();

  /**
   * Creates a client that should be initialized with
   * {@link #initialize(LdapConnectionFactory, String) initialize} method.
   */
  public SlcwClient() {

  }

  /**
   * Creates a client with an opened connection.
   *
   * @param connection an opened connection to the server.
   * @param baseDn     the starting point on the server.
   */
  public SlcwClient(final LDAPConnection connection, final String baseDn) {
    this.baseDn = baseDn;
    this.connection = connection;
  }

  /**
   * Authenticate a user and open the client connection, otherwise throws an exception.
   *
   * @param factory {@link LdapConnectionFactory}.
   */
  public void initialize(final LdapConnectionFactory factory, final String baseDn) {
    this.baseDn = baseDn;
    this.connection = factory.openConnection();
  }

  /**
   * Creates a new entry (record) in the structure.*
   *
   * @param object an object that you want to save in the structure.
   * @param <T>    is a conventional letter that stands for "Type".
   * @return a result of an adding operation. (ex. "0 (success)").
   */
  public <T> OperationResult add(final T object) {
    final SlcwEntry entry = new SlcwEntry(baseDn);
    mapper.map(object, entry);
    entry.setAttributes(SlcwConverter.convertFieldsToAttributes(entry));

    final LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    return executor.executeAddOperation(entry);
  }

  /**
   * Alter the content of an entry (record) in the structure.
   *
   * @param object an object that you want to modify in the structure.
   * @param <T>    is a conventional letter that stands for "Type".
   * @return a result of an adding operation. (ex. "0 (success)").
   */
  public <T> OperationResult update(final T object) {
    final SlcwEntry entry = new SlcwEntry(baseDn);
    mapper.map(object, entry);
    entry.setAttributes(SlcwConverter.convertFieldsToModifications(entry));

    final LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    return executor.executeUpdateOperation(entry);
  }

  /**
   * Remove an entry (record) from the structure.
   *
   * @param object an object that you want to delete from the structure.
   * @param <T>    is a conventional letter that stands for "Type".
   * @return a result of an adding operation. (ex. "0 (success)").
   */
  public <T> OperationResult delete(final T object) {
    final SlcwEntry entry = new SlcwEntry(baseDn);
    mapper.map(object, entry);

    final LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    return executor.executeDeleteOperation(entry);
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
  public <T> T getById(final String id, final Class<T> clazz) {
    final T object = ObjectFactory.newObject(clazz);
    final SlcwEntry entry = new SlcwEntry(baseDn);
    entry.getId().setPropertyValue(id);
    mapper.map(object, entry);

    final LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    final LdapOperationResult result = executor.executeGetOperation(entry);
    entry.setAttributes(result.getEntries().get(0).getAttributes());

    mapper.map(entry, object);
    return object;
  }

  /**
   * Counts the number of entries in the structure based on filter conditions.
   *
   * @param filter {@link Filter}.
   * @return number of matching entries.
   */
  public long countBy(final Filter filter) {
    if (filter.getDn() != null && !Objects.equals(filter.getDn(), baseDn)) {
      filter.setDn(filter.getDn() + "," + baseDn);
    } else {
      filter.setDn(baseDn);
    }

    final LdapOperationExecutor executor = new LdapOperationExecutor(connection);
    final LdapOperationResult result = executor.executeCountOperation(filter);
    return result.getEntriesReturned();
  }
}
