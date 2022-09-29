package com.zextras.utils;

import java.lang.reflect.InvocationTargetException;

/**
 * Helper util class that creates new objects.
 */
public class ObjectFactory {

  /**
   * Create a new object of a given class if the class has a default constructor, otherwise throws
   * an exception.
   *
   * @param clazz type of the class which represents an object that you wanted to get.
   * @param <T>   is a conventional letter that stands for "Type".
   * @return a new object of a given class.
   */
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
