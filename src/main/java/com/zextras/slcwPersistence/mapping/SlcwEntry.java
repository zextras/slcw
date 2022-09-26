package com.zextras.slcwPersistence.mapping;

import com.unboundid.ldap.sdk.SearchResultEntry;

import java.util.HashMap;
import java.util.Map;


public class SlcwEntry {
    private String dn;
    private SlcwField id;
    private SlcwField objectClass;
    private Map<String, SlcwField> fields = new HashMap<>();
    private SearchResultEntry searchResultEntry;

    public void setId(SlcwField id) {
        this.id = id;
    }

    public void setObjectClass( SlcwField objectClass) {
        this.objectClass = objectClass;
    }

    public void setFields(Map<String, SlcwField> fields) {
        this.fields = fields;
    }

    public SlcwField getId() {
        return id;
    }

    public SlcwField getObjectClass() {
        return objectClass;
    }

    public Map<String, SlcwField> getFields() {
        return fields;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public SearchResultEntry getSearchResultEntry() {
        return searchResultEntry;
    }

    public void setSearchResultEntry(SearchResultEntry searchResultEntry) {
        this.searchResultEntry = searchResultEntry;
    }
}
