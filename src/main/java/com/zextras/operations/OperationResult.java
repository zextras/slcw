package com.zextras.operations;

public class OperationResult {
    private String name;
    private int intValue;
    private String stringRepresentation;

    public OperationResult(String name, int intValue) {
        this.name = name;
        this.intValue = intValue;
        this.stringRepresentation = intValue + " (" + name + ')';
    }

    public String getName() {
        return name;
    }

    public int getIntValue() {
        return intValue;
    }

    public String getStringRepresentation() {
        return stringRepresentation;
    }
}
