package com.zextras.persistence.converters;

import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.zextras.SlcwBean;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.annotations.Column;
import com.zextras.persistence.annotations.Id;
import com.zextras.persistence.annotations.Table;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Helper class that performs converting operations.
 */
public class SlcwConverter<T> {

  /**
   * Private constructor used to restrict someone from instantiating utility class.
   */
  private SlcwConverter() {
    throw new SlcwException("SlcwConverter class cannot be instantiated.");
  }

  public static <T extends SlcwBean> List<T> convertEntriesToBeans(List<SearchResultEntry> entries, Class<T> clazz) {
    return entries.stream().map(
        entry -> convertEntryToBean(entry, clazz)
    ).collect(Collectors.toList());
  }

  public static <T extends SlcwBean> T convertEntryToBean(Entry entry, Class<T> clazz) {
    try {
      final T bean = clazz.getDeclaredConstructor().newInstance();
      bean.setDn(entry.getDN());
      Arrays.stream(clazz.getDeclaredFields()).forEach(
            field -> {
              field.setAccessible(true);
              String key = null;
              if (field.isAnnotationPresent(Id.class)) {
                var annotationProperty = field.getAnnotation(Id.class);
                key = annotationProperty.name();
              } else if (field.isAnnotationPresent(
                  com.zextras.persistence.annotations.Column.class)) {
                var annotationProperty = field.getAnnotation(Column.class);
                key = annotationProperty.name();
              }
              if (!Objects.isNull(key)) {
                try {
                  BeanUtils.setProperty(bean, field.getName(), entry.getAttribute(key).getValue());
                } catch (IllegalAccessException e) {
                  e.printStackTrace();
                } catch (InvocationTargetException e) {
                  e.printStackTrace();
                }
              }
            }
      );
      return bean;
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    return null;
  }

  //TODO: handle LDAP binary attributes
  /**
   * Converts an entry to a list of attributes which this entry has.
   * It adds also objectclass in attributes.
   *
   * @return list of attributes that can be stored.
   */
  public static List<Attribute> convertBeanToAttributes(SlcwBean object) {
    List<Attribute> attributes = new ArrayList<>();
    final Table table = object.getClass().getAnnotation(Table.class);
    attributes.add(new Attribute("objectclass", table.objectClass()));
    var declaredFields = object.getClass().getDeclaredFields();
    Arrays.stream(declaredFields)
        .forEach(
            field -> {
              field.setAccessible(true);
              String attributeName = null;
              if (field.isAnnotationPresent(Id.class)) {
                var annotationProperty = field.getAnnotation(Id.class);
                attributeName = annotationProperty.name();
              } else if (field.isAnnotationPresent(
                  com.zextras.persistence.annotations.Column.class)) {
                var annotationProperty = field.getAnnotation(Column.class);
                attributeName = annotationProperty.name();
              }
              if (!(Objects.isNull(attributeName))) {
                try {
                  attributes.add(new Attribute(attributeName, BeanUtils.getSimpleProperty(object, field.getName())));
                } catch (IllegalAccessException e) {
                  e.printStackTrace();
                } catch (InvocationTargetException e) {
                  e.printStackTrace();
                } catch (NoSuchMethodException e) {
                  e.printStackTrace();
                }
              }
            });
    return attributes;
  }

  //TODO: handle LDAP binary attributes

  /**
   * Converts an entry to a list of modifications which you want to apply.*
   *
   * @param object a representation of a record in the structure.
   * @return list of modifications that can be stored.
   */
  public static List<Modification> convertFieldsToModifications(SlcwBean object) {
    List<Modification> attributes = new ArrayList<>();
    var declaredFields = object.getClass().getDeclaredFields();
    Arrays.stream(declaredFields)
        .forEach(
            field -> {
              field.setAccessible(true);
              String attributeName = null;
              if (field.isAnnotationPresent(Id.class)) {
                var annotationProperty = field.getAnnotation(Id.class);
                attributeName = annotationProperty.name();
              } else if (field.isAnnotationPresent(
                  com.zextras.persistence.annotations.Column.class)) {
                var annotationProperty = field.getAnnotation(Column.class);
                attributeName = annotationProperty.name();
              }
              if (!(Objects.isNull(attributeName))) {
                try {
                  attributes.add(new Modification(ModificationType.REPLACE, attributeName,
                      BeanUtils.getSimpleProperty(object, field.getName())));
                } catch (IllegalAccessException e) {
                  e.printStackTrace();
                } catch (InvocationTargetException e) {
                  e.printStackTrace();
                } catch (NoSuchMethodException e) {
                  e.printStackTrace();
                }
              }
            });
    return attributes;
  }
}
