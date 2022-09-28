package com.zextras.persistence.converters;

import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.zextras.persistence.mapping.SlcwEntry;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class that performs converting operations.
 */
public class SlcwConverter {
    /**
     * Converts entry to a list of attributes which this entry has.*
     *
     * @param entry a representation of a record in the structure
     * @return list of attributes that can be stored
     */
    public static List<Attribute> convertFieldsToAttributes(SlcwEntry entry) {
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
    //todo converter interface with different implementations
    public static List<Modification> convertFieldsToModifications(SlcwEntry entry) {
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
