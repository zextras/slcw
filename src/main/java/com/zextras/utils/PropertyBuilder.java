package com.zextras.utils;

import com.zextras.persistence.annotations.Table;
import com.zextras.persistence.mapping.SlcwEntry;

//todo ?smthelse
public class PropertyBuilder {

  private StringBuilder builder = new StringBuilder();

  public <T> String buildDn(SlcwEntry entry, T object) {
    builder.delete(0, builder.length());

    return builder
        .append(object.getClass().getAnnotation(Table.class).property())
        .append("=")
        .append(object.getClass().getAnnotation(Table.class).name())
        .append(",")
        .append(entry.getBaseDn())
        .toString();
  }

  public <T> String buildFilter(SlcwEntry entry) {
    builder.delete(0, builder.length());

    return builder
        .append(entry.getId().getFieldName())
        .append("=")
        .append(entry.getId().getFiledValue())
        .toString();
  }
}
