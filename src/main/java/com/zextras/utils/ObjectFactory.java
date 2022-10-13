package com.zextras.utils;

import com.zextras.persistence.SlcwException;
import java.lang.reflect.InvocationTargetException;

/**
 * Helper util class that creates new objects.
 */
public class ObjectFactory {

  /**
   * Private constructor used to restrict someone from instantiating utility class.
   */
  private ObjectFactory() {
    throw new SlcwException("Utility class cannot be instantiated.");
  }

  /**
   * Create a new object of a given class if the class has a default constructor, otherwise throws
   * an exception.
   *
   * @param clazz type of the class which represents an object that you wanted to get.
   * @param <T>   is a conventional letter that stands for "Type".
   * @return a new object of a given class.
   */
  public static <T> T newObject(final Class<T> clazz) {
    final T object;
    try {
      object = clazz.getDeclaredConstructor().newInstance();
    } catch (final InstantiationException
                   | IllegalAccessException
                   | NoSuchMethodException
                   | InvocationTargetException e) {
      throw new SlcwException(e.getMessage());
    }
    return object;
  }
}
