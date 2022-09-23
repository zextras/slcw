package com.zextras.slcwPersistence.mapping;

public class Field {
    private final String fieldName;
    private final Object filedValue;

    public Field(String fieldName, Object filedValue) {
        this.fieldName = fieldName;
        this.filedValue = filedValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFiledValue() {
        return filedValue;
    }
}
