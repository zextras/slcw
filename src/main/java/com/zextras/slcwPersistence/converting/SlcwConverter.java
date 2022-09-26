package com.zextras.slcwPersistence.converting;

import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.zextras.slcwPersistence.mapping.SlcwEntry;

import java.util.List;
import java.util.stream.Collectors;

public class SlcwConverter {

    public static<T> void convertFromSearchResult(SlcwEntry entry, T object) {
        entry.getSearchResultEntry().getAttributes()
                .forEach(attribute -> {
                    var field = entry.getFields().get(attribute.getName());
                    if (field != null) {
                    var fieldName = field.getFieldName();
                    java.lang.reflect.Field declaredField;
                    try {
                      declaredField  = object.getClass().getDeclaredField(fieldName);
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    declaredField.setAccessible(true);
                    try {
                        //todo parse from string
                        declaredField.set(object, attribute.getValue());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }});
    }

    public static List<Attribute> convertToAttributes(SlcwEntry entry) {
        return entry.getFields().entrySet().stream()
                .map(field -> {
                    var key = field.getKey();
                    var value = field.getValue().getFiledValue();

                    if (field.getValue().isBinary()) {
                        return new Attribute(key, (byte[]) value);
                    } else {
                        return new Attribute(key, String.valueOf(value));
                    }
                }).collect(Collectors.toList());
    }

    public static List<Modification> convertToModifications(SlcwEntry entry) {
        return entry.getFields().entrySet().stream()
                .map(field -> {
                    var key = field.getKey();
                    var value = field.getValue().getFiledValue();

                    if (field.getValue().isBinary()) {
                        return new Modification(ModificationType.REPLACE, key, (byte[]) value);
                    } else {
                        return new Modification(ModificationType.REPLACE, key, String.valueOf(value));
                    }
                }).collect(Collectors.toList());

    }

}
