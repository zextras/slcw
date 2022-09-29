package com.zextras.utils;

import java.lang.reflect.InvocationTargetException;

public class ObjectFactory {

  public static <T> T newObject(Class<T> clazz) {
    T object;
    try {
      object = clazz.getDeclaredConstructor().newInstance();
    } catch (InstantiationException
             | IllegalAccessException
             | NoSuchMethodException
             | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
    return object;
  }
}
