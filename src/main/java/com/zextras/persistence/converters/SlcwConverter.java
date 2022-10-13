package com.zextras.persistence.converters;

import com.unboundid.ldap.sdk.Attribute;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.zextras.persistence.SlcwException;
import com.zextras.persistence.mapping.entries.SlcwEntry;
import java.util.List;
import java.util.stream.Collectors;

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

  /**
   * Converts an entry to a list of attributes which this entry has.*
   *
   * @param entry a representation of a record in the structure.
   * @return list of attributes that can be stored.
   */
  public static List<Attribute> convertFieldsToAttributes(final SlcwEntry entry) {
    return entry.getFields().entrySet().stream()
        .map(field -> {
          final var key = field.getKey();
          final var value = field.getValue().getPropertyValue();

          if (field.getValue().isBinary()) {
            return new Attribute(key, (byte[]) value);
          } else {
            return new Attribute(key, String.valueOf(value));
          }
        }).collect(Collectors.toList());
  }

  //todo converter interface with different implementations

  /**
   * Converts an entry to a list of modifications which you want to applyFilter.*
   *
   * @param entry a representation of a record in the structure.
   * @return list of modifications that can be stored.
   */
  public static List<Modification> convertFieldsToModifications(final SlcwEntry entry) {
    return entry.getFields().entrySet().stream()
        .map(field -> {
          final var key = field.getKey();
          final var value = field.getValue().getPropertyValue();

          if (field.getValue().isBinary()) {
            return new Modification(ModificationType.REPLACE, key, (byte[]) value);
          } else {
            return new Modification(ModificationType.REPLACE, key, String.valueOf(value));
          }
        }).collect(Collectors.toList());
  }
}
