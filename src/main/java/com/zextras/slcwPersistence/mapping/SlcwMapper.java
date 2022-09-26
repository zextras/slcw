package com.zextras.slcwPersistence.mapping;

import com.zextras.slcwPersistence.annotations.*;

import java.util.*;

public class SlcwMapper {
    public static <T> void map(T object, SlcwEntry entry) {
        //todo check if its entity
        mapFields(object, entry);

        String dn = entry.getId().getFieldName()
                + "="
                + entry.getId().getFiledValue()
                + ","
                + object.getClass().getAnnotation(Table.class).property()
                + "="
                + object.getClass().getAnnotation(Table.class).name()
                + ",dc=example,dc=com"; //todo how better set baseDn

        entry.setDn(dn);
    }

    public static <T> void map(SlcwEntry entry, T object) {
        //todo check if its entity
        mapFields(object, entry);

        String dn = object.getClass().getAnnotation(Table.class).property()
                + "="
                + object.getClass().getAnnotation(Table.class).name()
                + ",dc=example,dc=com"; //todo how better set baseDn

        entry.setDn(dn);
    }

    public static <T> void mapFields(T object, SlcwEntry entry) {
        var mapEntry = entry.getFields();

        var declaredFields = object.getClass().getDeclaredFields();
        Arrays.stream(declaredFields)
                .forEach(field -> {
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
                        entry.setObjectClass(new SlcwField(key, value));
                        mapEntry.put(key, new SlcwField(key, value));
                    } else if (field.isAnnotationPresent(
                            com.zextras.slcwPersistence.annotations.Column.class)) {
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

}
