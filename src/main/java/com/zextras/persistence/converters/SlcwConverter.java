package com.zextras.persistence.converters;

import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.zextras.SlcwBean;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.annotations.Column;
import com.zextras.persistence.annotations.Id;
import com.zextras.persistence.annotations.ObjectClass;
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
public class SlcwConverter {

  /**
   * Private constructor used to restrict someone from instantiating utility class.
   */
  private SlcwConverter() {
    throw new SlcwException("SlcwConverter class cannot be instantiated.");
  }

  //TODO: handle LDAP binary attributes
  /**
   * Converts an entry to a list of attributes which this entry has.*
   *
   * @return list of attributes that can be stored.
   */
  public static List<Attribute> convertFieldsToAttributes(SlcwBean object) {
    List<Attribute> attributes = new ArrayList<>();
    var declaredFields = object.getClass().getDeclaredFields();
    Arrays.stream(declaredFields)
        .forEach(
            field -> {
              field.setAccessible(true);
              String key = null;
              if (field.isAnnotationPresent(Id.class)) {
                var annotationProperty = field.getAnnotation(Id.class);
                key = annotationProperty.name();
              } else if (field.isAnnotationPresent(ObjectClass.class)) {
                var annotationProperty = field.getAnnotation(ObjectClass.class);
                key = annotationProperty.name();
              } else if (field.isAnnotationPresent(
                  com.zextras.persistence.annotations.Column.class)) {
                var annotationProperty = field.getAnnotation(Column.class);
                key = annotationProperty.name();
              }
              if (!(Objects.isNull(key))) {
                try {
                  attributes.add(new Attribute(key, BeanUtils.getSimpleProperty(object, field.getName())));
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
              String key = null;
              if (field.isAnnotationPresent(Id.class)) {
                var annotationProperty = field.getAnnotation(Id.class);
                key = annotationProperty.name();
              } else if (field.isAnnotationPresent(ObjectClass.class)) {
                var annotationProperty = field.getAnnotation(ObjectClass.class);
                key = annotationProperty.name();
              } else if (field.isAnnotationPresent(
                  com.zextras.persistence.annotations.Column.class)) {
                var annotationProperty = field.getAnnotation(Column.class);
                key = annotationProperty.name();
              }
              if (!(Objects.isNull(key))) {
                try {
                  attributes.add(new Modification(ModificationType.REPLACE, key,
                      BeanUtils.getSimpleProperty(object, key)));
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
