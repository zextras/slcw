package com.zextras.persistence.mapping;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class which represents a record in the given structure.
 */
public class SlcwEntry {
    private String baseDn;
    private String dn;
    private SlcwField id;
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

    public SlcwField getId() {
        return id;
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

    public String getBaseDn() {
        return baseDn;
    }

    public void setBaseDn(String baseDn) {
        this.baseDn = baseDn;
    }

    public Collection<?> getAttributes() {
        return attributes;
    }

    public void setAttributes(Collection<?> attributes) {
        this.attributes = attributes;
    }
}
