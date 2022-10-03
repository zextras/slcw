package com.zextras.utils;

import com.zextras.persistence.mapping.SlcwEntry;

/**
 * Helper util class that performs string building operations.
 */
public class PropertyBuilder {

  private final StringBuilder builder = new StringBuilder();

  /**
   * Builds a string representation of object distinguished name (DN). The LDAP API references an
   * LDAP object by its distinguished name. A DN is a sequence of relative distinguished names (RDN)
   * connected by commas.
   *
   * @param entry an object that represents a record in LDAP.
   * @return a string DN.
   */
  public String buildDn(SlcwEntry entry) {
    //todo ?smthelse
    builder.delete(0, builder.length());

    return builder
        .append(entry.getTable().getPropertyName())
        .append("=")
        .append(entry.getTable().getPropertyValue())
        .append(",")
        .append(entry.getBaseDn())
        .toString();
  }

  /**
   * Builds a string representation of a filter that you want to apply.
   *
   * @param entry an object that represents a record in LDAP.
   * @return a string filter.
   */
  public String buildFilter(SlcwEntry entry) {
    builder.delete(0, builder.length());

    return builder
        .append(entry.getId().getPropertyName())
        .append("=")
        .append(entry.getId().getPropertyValue())
        .toString();
  }
}
