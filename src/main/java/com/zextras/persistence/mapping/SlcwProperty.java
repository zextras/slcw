package com.zextras.persistence.mapping;

/**
 * Class which represents a property of an object.
 */
public class SlcwProperty {

  private String propertyName;
  private Object propertyValue;
  private boolean binary;

  public SlcwProperty() {
  }

  public SlcwProperty(String propertyName, Object propertyValue, boolean binary) {
    this.propertyName = propertyName;
    this.propertyValue = propertyValue;
    this.binary = binary;
  }

  public SlcwProperty(String propertyName, Object propertyValue) {
    this.propertyName = propertyName;
    this.propertyValue = propertyValue;
  }

  public boolean isBinary() {
    return binary;
  }

  public Object getPropertyValue() {
    return propertyValue;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  public void setPropertyValue(Object propertyValue) {
    this.propertyValue = propertyValue;
  }

  public void setBinary(boolean binary) {
    this.binary = binary;
  }
}
