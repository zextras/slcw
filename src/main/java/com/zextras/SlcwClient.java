package com.zextras;

import com.unboundid.ldap.sdk.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** FIXME add actual documentation when it will be implemented */
public class SlcwClient {
  private LDAPConnection connection;

  public SlcwClient(final LDAPConnection connection) {
    this.connection = connection;
  }

  public SlcwClient(final String host, final int port, final String bindDN, final String password) {
    openConnection(host, port, bindDN, password);
  }

  private void openConnection(final String host, final int port, final String bindDN, final String password) {
    try {
      connection = new LDAPConnection(host, port, bindDN, password);
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  public LDAPResult add(final String dn, final String objectClass, final SlcwBean bean) {
    List<Attribute> attributes = toAttributeList(objectClass, bean);
    try {
      return connection.add(dn, attributes);
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  public LDAPResult modify(final String dn, final SlcwBean bean) {
    List<Modification> modifications = toModificationList(bean);
    try {
      return connection.modify(dn, modifications);
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  public LDAPResult delete(final String dn) {
    try {
      return connection.delete(dn);
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

  private List<Attribute> toAttributeList(final String objectClass, final SlcwBean bean) {
    Map<String, Object> beanEntry = bean.toMap();

    List<Attribute> attributes = new ArrayList<>();
    attributes.add(new Attribute("objectClass", objectClass));

    for (Map.Entry<String, Object> entry : beanEntry.entrySet()) {
      if (entry.getValue() instanceof byte[]) {
        attributes.add(new Attribute(entry.getKey(), (byte[]) entry.getValue()));
      } else {
        attributes.add(new Attribute(entry.getKey(), entry.getValue().toString()));
      }
    }

    return attributes;
  }

  private List<Modification> toModificationList(final SlcwBean bean) {
    Map<String, Object> beanEntry = bean.toMap();

    List<Modification> modifications = new ArrayList<>();
    for (Map.Entry<String, Object> entry : beanEntry.entrySet()) {
      if (entry.getValue() instanceof byte[]) {
        modifications.add(
            new Modification(ModificationType.REPLACE, entry.getKey(), (byte[]) entry.getValue()));
      } else {
        modifications.add(
            new Modification(
                ModificationType.REPLACE, entry.getKey(), entry.getValue().toString()));
      }
    }
    return modifications;
  }
}
