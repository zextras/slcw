package com.zextras;

import com.unboundid.ldap.sdk.*;
import com.zextras.handler.SlcwException;
import com.zextras.handler.OperationResult;
import com.zextras.persistence.converting.SlcwConverter;
import com.zextras.persistence.mapping.SlcwEntry;
import com.zextras.persistence.mapping.SlcwMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Main entrypoint for the library.
 */
public class SlcwClient {
  private LDAPConnection connection;
  private final String baseDn;

  /**
   * Creates a client with an opened connection.*
   *
   * @param connection an opened connection to the server.
   * @param baseDn the starting point on the server.
   */
  public SlcwClient(LDAPConnection connection, String baseDn) {
    this.baseDn = baseDn;
    this.connection = connection;
  }

  /**
   * Creates a client, authenticate a user and change the identity of the client connection.*
   *
   * @param host a network layer host address.
   * @param port a port on a host that connects it to the storage system.
   * @param bindDN a Username used to connect to the server.
   * @param password a secret word or phrase that allows access to the server.
   * @param baseDn the starting point on the server.
   */
  public SlcwClient(String host, int port, String bindDN, String password, String baseDn) {
    this.baseDn = baseDn;
    connect(host, port, bindDN, password);
  }

  private void connect(String host, int port, String bindDN, String password) {
    try {
      connection = new LDAPConnection(host, port, bindDN, password);
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets an object from a record stored in the structure, otherwise throws an exception.*
   *
   * @param id a unique identifier that marks that particular record as unique from every other record.
   * @param clazz type of the class which represents an object that you wanted to get. (ex. User.class)
   * @return an object of the given class.
   * @param <T> is a conventional letter that stands for "Type".
   */
  public <T> T getById(String id, Class<T> clazz) {
    T object;
    try {
      object = clazz.getDeclaredConstructor().newInstance();
    } catch (InstantiationException
        | IllegalAccessException
        | NoSuchMethodException
        | InvocationTargetException e) {
      throw new RuntimeException(e);
    }

    SlcwEntry entry = new SlcwEntry(baseDn);
    SlcwMapper.map(entry, object);

    String filter = entry.getId().getFieldName() + "=" + id;

    var searchResult = search(entry.getDn(), SearchScope.ONE, filter);

    if (searchResult.isEmpty()) {
      throw new SlcwException(String.format("Object %s not found.", id));
    }
    var searchResultEntry = searchResult.get(0);
    SlcwMapper.mapSearchResult(searchResultEntry, entry, object);
    return object;
  }

  /**
   * Creates a new entry in the structure.*
   *
   * @param object an object that you want to save in the structure.
   * @return a result of an adding operation. (ex. "0 (success)").
   * @param <T> is a conventional letter that stands for "Type".
   */
  public <T> OperationResult add(T object) {
    SlcwEntry entry = new SlcwEntry(baseDn);
    SlcwMapper.map(object, entry);

    List<Attribute> attributes = SlcwConverter.convertToAttributes(entry);
    try {
      LDAPResult result =  connection.add(entry.getDn(), attributes);
      return new OperationResult(result.getResultCode().getName(), result.getResultCode().intValue());
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Alter the content of an entry in the structure.*
   *
   * @param object an object that you want to modify in the structure.
   * @return a result of an adding operation. (ex. "0 (success)").
   * @param <T> is a conventional letter that stands for "Type".
   */
  public <T> OperationResult update(T object) {
    SlcwEntry entry = new SlcwEntry(baseDn);
    SlcwMapper.map(object, entry);
    List<Modification> modifications = SlcwConverter.convertToModifications(entry);
    try {
      LDAPResult result = connection.modify(entry.getDn(), modifications);
      return new OperationResult(result.getResultCode().getName(), result.getResultCode().intValue());
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Remove an entry from the structure.*
   *
   * @param object an object that you want to remove.
   * @return a result of an adding operation. (ex. "0 (success)").
   * @param <T> is a conventional letter that stands for "Type".
   */
  public <T> OperationResult delete(T object) { //todo change for id
    SlcwEntry entry = new SlcwEntry(baseDn);
    SlcwMapper.map(object, entry);
    try {
      LDAPResult result = connection.delete(entry.getDn());
      return new OperationResult(result.getResultCode().getName(), result.getResultCode().intValue());
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  private List<SearchResultEntry> search(String baseDN, SearchScope searchScope, String filter) {
    SearchResult searchResult;
    try {
      searchResult = connection.search(baseDN, searchScope, filter);
    } catch (LDAPSearchException e) {
      throw new RuntimeException(e);
    }
    return searchResult.getSearchEntries();
  }
}
