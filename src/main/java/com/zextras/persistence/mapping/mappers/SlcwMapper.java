package com.zextras.persistence.mapping.mappers;

import com.unboundid.ldap.sdk.Attribute;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.annotations.*;
import com.zextras.persistence.mapping.SlcwProperty;
import com.zextras.persistence.mapping.entries.SlcwEntry;
import com.zextras.utils.PropertyBuilder;
import com.zextras.utils.ReflectionUtils;
import java.util.*;

/**
 * Helper class that performs mapping operations specific for Ldap.
 */
public class SlcwMapper implements Mapper<SlcwEntry> {

  private final PropertyBuilder builder = new PropertyBuilder();

  /**
   * Maps an object to the representation entry in matter to perform CRUD operations.*
   *
   * @param object object of the Type - a source object that you want to map.
   * @param entry  a destination object of mapping.
   * @param <T>    is a conventional letter that stands for "Type".
   */

  @Override
  public <T> void map(T object, SlcwEntry entry) {
    if (!object.getClass().isAnnotationPresent(Entity.class)) {
      throw new SlcwException("Class should be mark with @Entity annotation.");
    }

    if (!object.getClass().isAnnotationPresent(Table.class)) {
      throw new SlcwException("Class should be mark with @Table annotation.");
    }

    entry.setTable(new SlcwProperty(
        object.getClass().getAnnotation(Table.class).property(),
        object.getClass().getAnnotation(Table.class).name()));

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
                  throw new SlcwException(e.getMessage());
                }
                SlcwProperty id = entry.getId();
                id.setPropertyName(field.getName());
                if (id.getPropertyValue() == null) {
                  id.setPropertyValue(value);
                }
                mapEntry.put(key, id);
              } else if (field.isAnnotationPresent(ObjectClass.class)) {
                var annotationProperty = field.getAnnotation(ObjectClass.class);
                var key = annotationProperty.name();
                Object value;
                try {
                  value = (field.get(object));
                } catch (IllegalAccessException e) {
                  throw new SlcwException(e.getMessage());
                }
                mapEntry.put(key, new SlcwProperty(key, value));
              } else if (field.isAnnotationPresent(
                  com.zextras.persistence.annotations.Column.class)) {
                var annotationProperty = field.getAnnotation(Column.class);
                var key = annotationProperty.name();
                boolean binary = annotationProperty.binary();
                Object value;
                try {
                  value = field.get(object);
                } catch (IllegalAccessException e) {
                  throw new SlcwException(e.getMessage());
                }
                mapEntry.put(key, new SlcwProperty(field.getName(), value, binary));
              }
            });

    entry.setDn(builder.buildDn(entry));
    entry.setFilter(builder.buildFilter(entry));
  }

  /**
   * Maps a representation entry in the structure to a Java object in order to perform get
   * operation.*
   *
   * @param entry  representation entry of an object.
   * @param object object of the Type that you want to get.
   * @param <T>    is a conventional letter that stands for "Type".
   */
  @Override
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
                var fieldName = field.getPropertyName();
                java.lang.reflect.Field declaredField;
                try {
                  declaredField = object.getClass().getDeclaredField(fieldName);
                } catch (NoSuchFieldException e) {
                  throw new SlcwException(e.getMessage());
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
