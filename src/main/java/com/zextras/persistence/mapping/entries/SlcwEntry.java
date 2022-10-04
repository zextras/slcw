package com.zextras.persistence.mapping.entries;

import com.zextras.persistence.mapping.SlcwProperty;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class which represents a record in the given Ldap structure.
 */
public class SlcwEntry extends Entry {

  private final String baseDn;
  private String dn;
  private String filter;
  private SlcwProperty table;
  private SlcwProperty id = new SlcwProperty();
  private Map<String, SlcwProperty> fields = new HashMap<>();
  private Collection<?> attributes;

  public SlcwEntry(String baseDn) {
    this.baseDn = baseDn;
  }

  public void setId(SlcwProperty id) {
    this.id = id;
  }

  public void setFields(Map<String, SlcwProperty> fields) {
    this.fields = fields;
  }

  public void setDn(String dn) {
    this.dn = dn;
  }

  public void setAttributes(Collection<?> attributes) {
    this.attributes = attributes;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public void setTable(SlcwProperty table) {
    this.table = table;
  }

  public SlcwProperty getId() {
    return id;
  }

  public Map<String, SlcwProperty> getFields() {
    return fields;
  }

  public String getDn() {
    return dn;
  }

  public String getBaseDn() {
    return baseDn;
  }

  public Collection<?> getAttributes() {
    return attributes;
  }

  public String getFilter() {
    return filter;
  }

  public SlcwProperty getTable() {
    return table;
  }
}
