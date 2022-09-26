package com.zextras.slcwPersistence.converting;

import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.zextras.slcwPersistence.mapping.SlcwEntry;

import java.util.List;
import java.util.stream.Collectors;

public class SlcwConverter {
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
