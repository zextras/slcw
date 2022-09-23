package com.zextras.slcwPersistence.mapping;

import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.zextras.SlcwBean;
import com.zextras.slcwPersistence.annotations.ObjectClass;
import com.zextras.slcwPersistence.annotations.UID;
import java.util.*;
import java.util.stream.Collectors;

public class SlcwMapper {
  public static Map<String, List<Field>> mapFields(final SlcwBean bean) {
    var declaredFields = bean.getClass().getDeclaredFields();

    Map<String, List<Field>> map = new HashMap<>();

    List<Field> fields = new ArrayList<>();

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
                map.put("uid", List.of(new Field(key, value)));
              } else if (field.isAnnotationPresent(ObjectClass.class)) {
                var key = field.getAnnotation(ObjectClass.class).name();
                String value;
                try {
                  value = String.valueOf(field.get(bean));
                } catch (IllegalAccessException e) {
                  throw new RuntimeException(e);
                }
                fields.add(new Field(key, value));
              } else if (field.isAnnotationPresent(
                  com.zextras.slcwPersistence.annotations.Attribute.class)) {
                var key =
                    field
                        .getAnnotation(com.zextras.slcwPersistence.annotations.Attribute.class)
                        .name();
                String value;
                try {
                  value = String.valueOf(field.get(bean));
                } catch (IllegalAccessException e) {
                  throw new RuntimeException(e);
                }
                fields.add(new Field(key, value));
              }
            });
    map.put("fields", fields);
    return map;
  }

  public static List<Attribute> toAttributeList(List<Field> fields) {
    return fields.stream()
        .map(
            field -> {
              if (field.getFiledValue() instanceof byte[]) {
                return new Attribute(field.getFieldName(), (byte[]) field.getFiledValue());
              } else {
                return new Attribute(field.getFieldName(), String.valueOf(field.getFiledValue()));
              }
            })
        .collect(Collectors.toList());
  }

  public static List<Modification> toModificationList(List<Field> fields) {
    return fields.stream()
        .map(
            field -> {
              if (field.getFiledValue() instanceof byte[]) {
                return new Modification(
                    ModificationType.REPLACE, field.getFieldName(), (byte[]) field.getFiledValue());
              } else {
                return new Modification(
                    ModificationType.REPLACE,
                    field.getFieldName(),
                    String.valueOf(field.getFiledValue()));
              }
            })
        .collect(Collectors.toList());
  }
}
