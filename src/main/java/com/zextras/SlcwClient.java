package com.zextras;

import com.unboundid.ldap.sdk.*;
import com.zextras.slcwPersistence.converting.SlcwConverter;
import com.zextras.slcwPersistence.mapping.SlcwEntry;
import com.zextras.slcwPersistence.mapping.SlcwField;
import com.zextras.slcwPersistence.mapping.SlcwMapper;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/** FIXME add actual documentation when it will be implemented */
public class SlcwClient {
  private LDAPConnection connection;

  public SlcwClient(final LDAPConnection connection) {
    this.connection = connection;
  }

  public SlcwClient(final String host, final int port, final String bindDN, final String password) {
    connect(host, port, bindDN, password);
  }

  private void connect(
      final String host, final int port, final String bindDN, final String password) {
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
    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }

    SlcwEntry entry = new SlcwEntry();
    SlcwMapper.map(entry, object);

    String filter = entry.getId().getFieldName()
            + "="
            + id;

    var searchResult = search(entry.getDn(), SearchScope.ONE, filter).get(0);
    entry.setSearchResultEntry(searchResult);
    SlcwConverter.convertFromSearchResult(entry, object);
    return object;
  }

  public <T> LDAPResult add(final T object) {
    SlcwEntry entry = new SlcwEntry();
    SlcwMapper.map(object, entry);

    List<Attribute> attributes = SlcwConverter.convertToAttributes(entry);
    try {
      return connection.add(entry.getDn(), attributes);
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> LDAPResult update(final T object) {
    SlcwEntry entry = new SlcwEntry();
    SlcwMapper.map(object, entry);
    List<Modification> modifications = SlcwConverter.convertToModifications(entry);
    try {
      return connection.modify(entry.getDn(), modifications);
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

    public <T> LDAPResult delete(final T object) {
      SlcwEntry entry = new SlcwEntry();
      SlcwMapper.map(object, entry);
      try {
        return connection.delete(entry.getDn());
      } catch (LDAPException e) {
        throw new RuntimeException(e);
      }
    }

  public List<SearchResultEntry> search(final String baseDN, final SearchScope searchScope, final String filter) {
    SearchResult searchResult;
    try {
      searchResult = connection.search(baseDN, searchScope, filter);
    } catch (LDAPSearchException e) {
      throw new RuntimeException(e);
    }
    return searchResult.getSearchEntries();
  }
}
