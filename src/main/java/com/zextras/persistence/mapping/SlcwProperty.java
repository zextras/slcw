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

  public SlcwProperty(final String propertyName, final Object propertyValue, final boolean binary) {
    this.propertyName = propertyName;
    this.propertyValue = propertyValue;
    this.binary = binary;
  }

  public SlcwProperty(final String propertyName, final Object propertyValue) {
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

  public void setPropertyName(final String propertyName) {
    this.propertyName = propertyName;
  }

  public void setPropertyValue(final Object propertyValue) {
    this.propertyValue = propertyValue;
  }

  public void setBinary(final boolean binary) {
    this.binary = binary;
  }
}
