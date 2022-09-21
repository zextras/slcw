package com.zextras;

import com.unboundid.ldap.sdk.*;
import com.zextras.slcwPersistence.annotations.Entity;
import com.zextras.slcwPersistence.annotations.ObjectClass;
import com.zextras.slcwPersistence.annotations.UID;
import com.zextras.slcwPersistence.Uid;
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

  public LDAPResult add(final SlcwBean bean) {
    Map<String, Object> map = toAttributeList(bean);
    Uid uid = (Uid) map.get("uid");

    String dn =
        uid.getKey()
            + "="
            + uid.getValue()
            + ",ou="
            + bean.getClass().getAnnotation(Entity.class).name()
            + ",dc=example,dc=com";

    List<Attribute> attributes = (List<Attribute>) map.get("attributes");
    try {
      return connection.add(dn, attributes);
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  private Map<String, Object> toAttributeList(final SlcwBean bean) {
    var declaredFields = bean.getClass().getDeclaredFields();

    Map<String, Object> map = new HashMap<>();
    List<Attribute> attributes = new ArrayList<>();

    Arrays.stream(declaredFields)
        .forEach(
            field -> {
              field.setAccessible(true);
              if (field.isAnnotationPresent(UID.class)) {
                var key = field.getAnnotation(UID.class).name();
                String value;
                try {
                  value = String.valueOf(field.get(bean));
                } catch (IllegalAccessException e) {
                  throw new RuntimeException(e);
                }
                Uid uid = new Uid(key, value);
                map.put("uid", uid);
              } else if (field.isAnnotationPresent(ObjectClass.class)) {
                var key = field.getAnnotation(ObjectClass.class).name();
                String value;
                try {
                  value = String.valueOf(field.get(bean));
                } catch (IllegalAccessException e) {
                  throw new RuntimeException(e);
                }
                attributes.add(new Attribute(key, value));
              } else if (field.isAnnotationPresent(com.zextras.slcwPersistence.annotations.Attribute.class)) {
                var key = field.getAnnotation(com.zextras.slcwPersistence.annotations.Attribute.class).name();
                String value;
                try {
                  value = String.valueOf(field.get(bean));
                } catch (IllegalAccessException e) {
                  throw new RuntimeException(e);
                }
                attributes.add(new Attribute(key, value));
              }
            });

    map.put("attributes", attributes);
    return map;
  }

  //  public LDAPResult add(final String dn, final String objectClass, final SlcwBean bean) {
  //    List<Attribute> attributes = toAttributeList(objectClass, bean);
  //    try {
  //      return connection.add(dn, attributes);
  //    } catch (LDAPException e) {
  //      throw new RuntimeException(e);
  //    }
  //  }
  //
  //  public LDAPResult modify(final String dn, final SlcwBean bean) {
  //    List<Modification> modifications = toModificationList(bean);
  //    try {
  //      return connection.modify(dn, modifications);
  //    } catch (LDAPException e) {
  //      throw new RuntimeException(e);
  //    }
  //  }
  //
  //  public LDAPResult delete(final String dn) {
  //    try {
  //      return connection.delete(dn);
  //    } catch (LDAPException e) {
  //      throw new RuntimeException(e);
  //    }
  //  }
  //
  //  public List<SearchResultEntry> search(final String baseDN, final SearchScope searchScope,
  // final String filter) {
  //    SearchResult searchResult;
  //    try {
  //      searchResult = connection.search(baseDN, searchScope, filter);
  //    } catch (LDAPSearchException e) {
  //      throw new RuntimeException(e);
  //    }
  //    return searchResult.getSearchEntries();
  //  }
  //
  //  private List<Modification> toModificationList(final SlcwBean bean) {
  //    Map<String, Object> beanEntry = bean.toMap();
  //
  //    List<Modification> modifications = new ArrayList<>();
  //    for (Map.Entry<String, Object> entry : beanEntry.entrySet()) {
  //      if (entry.getValue() instanceof byte[]) {
  //        modifications.add(
  //            new Modification(ModificationType.REPLACE, entry.getKey(), (byte[])
  // entry.getValue()));
  //      } else {
  //        modifications.add(
  //            new Modification(
  //                ModificationType.REPLACE, entry.getKey(), entry.getValue().toString()));
  //      }
  //    }
  //    return modifications;
  //  }
}
