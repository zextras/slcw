package com.zextras.slcwPersistence.mapping;

public class SlcwField {
    private String fieldName;
    private Object filedValue;
    private boolean binary;

    public SlcwField(String fieldName, Object filedValue, boolean binary) {
        this.fieldName = fieldName;
        this.filedValue = filedValue;
        this.binary = binary;
    }

    public SlcwField(String fieldName, Object filedValue) {
        this.fieldName = fieldName;
        this.filedValue = filedValue;
    }

    public boolean isBinary() {
        return binary;
    }

    public Object getFiledValue() {
        return filedValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFiledValue(Object filedValue) {
        this.filedValue = filedValue;
    }

    public void setBinary(boolean binary) {
        this.binary = binary;
    }
}
