package com.zextras.persistence.mapping;

import com.unboundid.ldap.sdk.Attribute;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.annotations.*;
import com.zextras.utils.ReflectionUtils;
import java.util.*;

/**
 * Helper class that performs mapping operations.
 */
public class SlcwMapper {

  /**
   * Maps an object to the representation entry in matter to perform CRUD operations.*
   *
   * @param object object of the Type - a source object that you want to map.
   * @param entry  a destination object of mapping.
   * @param <T>    is a conventional letter that stands for "Type".
   */
  public <T> void map(T object, SlcwEntry entry) {
    if (!object.getClass().isAnnotationPresent(Entity.class)) {
      throw new SlcwException("Class should be mark with @Entity annotation.");
    }

    var mapEntry = entry.getFields();

    var declaredFields = object.getClass().getDeclaredFields();
    Arrays.stream(declaredFields)
        .forEach(
            field -> {
              field.setAccessible(true);
              if (field.isAnnotationPresent(Id.class)) {
                var annotationProperty = field.getAnnotation(Id.class);
                var key = annotationProperty.name();
                String value;
                try {
                  value = String.valueOf(field.get(object));
                } catch (IllegalAccessException e) {
                  throw new RuntimeException(e);
                }
                entry.setId(new SlcwField(key, value));
                mapEntry.put(key, new SlcwField(field.getName(), value));
              } else if (field.isAnnotationPresent(ObjectClass.class)) {
                var annotationProperty = field.getAnnotation(ObjectClass.class);
                var key = annotationProperty.name();
                Object value;
                try {
                  value = (field.get(object));
                } catch (IllegalAccessException e) {
                  throw new RuntimeException(e);
                }
                mapEntry.put(key, new SlcwField(key, value));
              } else if (field.isAnnotationPresent(
                  com.zextras.persistence.annotations.Column.class)) {
                var annotationProperty = field.getAnnotation(Column.class);
                var key = annotationProperty.name();
                boolean binary = annotationProperty.binary();
                Object value;
                try {
                  value = field.get(object);
                } catch (IllegalAccessException e) {
                  throw new RuntimeException(e);
                }
                mapEntry.put(key, new SlcwField(field.getName(), value, binary));
              }
            });
  }

  /**
   * Maps a representation entry in the structure to a Java object in order to perform get
   * operation.*
   *
   * @param entry  representation entry of an object.
   * @param object object of the Type that you want to get.
   * @param <T>    is a conventional letter that stands for "Type".
   */
  public <T> void map(SlcwEntry entry, T object) {
    if (!object.getClass().isAnnotationPresent(Entity.class)) {
      throw new SlcwException("Class should be mark with @Entity annotation.");
    }

    entry
        .getAttributes()
        .forEach(
            attribute -> {
              Attribute attribute0 = (Attribute) attribute;
              var field = entry.getFields().get(attribute0.getName());
              if (field != null) {
                var fieldName = field.getFieldName();
                java.lang.reflect.Field declaredField;
                try {
                  declaredField = object.getClass().getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                  throw new RuntimeException(e);
                }
                if (field.isBinary()) {
                  ReflectionUtils.setBinaryValue(
                      declaredField, object, attribute0.getValueByteArray());
                } else {
                  ReflectionUtils.setStringValue(declaredField, object, attribute0.getValue());
                }
              }
            });
  }
}
