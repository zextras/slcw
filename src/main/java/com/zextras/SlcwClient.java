package com.zextras;

import com.unboundid.ldap.sdk.*;
import com.zextras.slcwPersistence.annotations.UID;
import com.zextras.slcwPersistence.mapping.Field;
import com.zextras.slcwPersistence.annotations.Entity;
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

  //todo better design
  public <T extends SlcwBean> T getByUID(final String uid, final Class<T> clazz) {
    var fields = clazz.getDeclaredFields();
    var filter = Arrays.stream(fields).filter(field -> field.isAnnotationPresent(UID.class)).findFirst().map(field -> field.getAnnotation(UID.class).name() + "=" + uid);
    String baseDn = "ou=" + clazz.getAnnotation(Entity.class).name() + ",dc=example,dc=com";
    List<SearchResultEntry> searchResultEntries = search(baseDn, SearchScope.ONE, filter.get());
    SearchResultEntry entry = searchResultEntries.get(0);
    entry.getAttributes();
    T bean;
    try {
      bean = clazz.getDeclaredConstructor().newInstance();
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  public LDAPResult add(final SlcwBean bean) {
    Map<String, List<Field>> map = SlcwMapper.mapFields(bean);
    Field uid = map.get("uid").get(0);
    // todo
    String dn =
        uid.getFieldName()
            + "="
            + uid.getFiledValue()
            + ",ou="
            + bean.getClass().getAnnotation(Entity.class).name()
            + ",dc=example,dc=com";

    List<Attribute> attributes = SlcwMapper.toAttributeList(map.get("fields"));
    try {
      return connection.add(dn, attributes);
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

  public LDAPResult update(final SlcwBean bean) {
    Map<String, List<Field>> map = SlcwMapper.mapFields(bean);
    Field uid = map.get("uid").get(0);
    // todo
    String dn =
        uid.getFieldName()
            + "="
            + uid.getFiledValue()
            + ",ou="
            + bean.getClass().getAnnotation(Entity.class).name()
            + ",dc=example,dc=com";
    List<Modification> modifications = SlcwMapper.toModificationList(map.get("fields"));
    try {
      return connection.modify(dn, modifications);
    } catch (LDAPException e) {
      throw new RuntimeException(e);
    }
  }

    public LDAPResult delete(final SlcwBean bean) {
      Map<String, List<Field>> map = SlcwMapper.mapFields(bean);
      Field uid = map.get("uid").get(0);
      // todo
      String dn =
              uid.getFieldName()
                      + "="
                      + uid.getFiledValue()
                      + ",ou="
                      + bean.getClass().getAnnotation(Entity.class).name()
                      + ",dc=example,dc=com";
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
}
