package com.zextras.persistence.mapping;

import com.unboundid.ldap.sdk.SearchScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class which represents a record in the given structure.
 */
public class SlcwEntry {

  private String baseDn;
  private SlcwField id;
  private String dn;
  private String filter;
  private SearchScope searchScope;
  private Map<String, SlcwField> fields = new HashMap<>();
  private Collection<?> attributes;

  public SlcwEntry(String baseDn) {
    this.baseDn = baseDn;
  }

  public void setId(SlcwField id) {
    this.id = id;
  }

  public void setFields(Map<String, SlcwField> fields) {
    this.fields = fields;
  }

  public void setDn(String dn) {
    this.dn = dn;
  }

  public void setBaseDn(String baseDn) {
    this.baseDn = baseDn;
  }

  public void setAttributes(Collection<?> attributes) {
    this.attributes = attributes;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public void setSearchScope(SearchScope searchScope) {
    this.searchScope = searchScope;
  }

  public SlcwField getId() {
    return id;
  }

  public Map<String, SlcwField> getFields() {
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

  public SearchScope getSearchScope() {
    return searchScope;
  }
}
