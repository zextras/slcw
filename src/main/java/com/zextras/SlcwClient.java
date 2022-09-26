package com.zextras;

import com.unboundid.ldap.sdk.*;
import com.zextras.handler.SlcwException;
import com.zextras.persistence.converting.SlcwConverter;
import com.zextras.persistence.mapping.SlcwEntry;
import com.zextras.persistence.mapping.SlcwMapper;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/** FIXME add actual documentation when it will be implemented */
public class SlcwClient {
  private LDAPConnection connection;
  private String baseDn;

  public SlcwClient(LDAPConnection connection, String baseDn) {
    this.baseDn = baseDn;
    this.connection = connection;
  }

  public SlcwClient(String host, int port, String bindDN, String password, String baseDn) {
    this.baseDn = baseDn;
    connect(host, port, bindDN, password);
  }

  private void connect(final String host, final int port, final String bindDN, final String password) {
    try {
      connection = new LDAPConnection(host, port, bindDN, password);
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> T getById(final String id, final Class<T> clazz) {
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
      throw new SlcwException(String.format("Object %d not found.", id));
    }

    var searchResultEntry = searchResult.get(0);
    SlcwMapper.mapSearchResult(searchResultEntry, entry, object);
    return object;
  }

  public <T> LDAPResult add(final T object) {
    SlcwEntry entry = new SlcwEntry(baseDn);
    SlcwMapper.map(object, entry);

    List<Attribute> attributes = SlcwConverter.convertToAttributes(entry);
    try {
      return connection.add(entry.getDn(), attributes);
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> LDAPResult update(T object) {
    SlcwEntry entry = new SlcwEntry(baseDn);
    SlcwMapper.map(object, entry);
    List<Modification> modifications = SlcwConverter.convertToModifications(entry);
    try {
      return connection.modify(entry.getDn(), modifications);
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> LDAPResult delete(T object) {
    SlcwEntry entry = new SlcwEntry(baseDn);
    SlcwMapper.map(object, entry);
    try {
      return connection.delete(entry.getDn());
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  public List<SearchResultEntry> search(String baseDN, SearchScope searchScope, String filter) {
    SearchResult searchResult;
    try {
      searchResult = connection.search(baseDN, searchScope, filter);
    } catch (LDAPSearchException e) {
      throw new RuntimeException(e);
    }
    return searchResult.getSearchEntries();
  }
}
