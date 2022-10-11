package com.zextras.conection.factories;

import com.zextras.persistence.SlcwException;

/**
 * A parent class to all the config classes that are used in order to open a needed connection.
 */
public class AbstractConnectionFactory<T> implements ConnectionFactory<T> {

  protected AbstractConnectionFactory() {
  }

  /**
   * The method should be overridden by children classes.
   *
   * @return if the method isn't overridden by a child class throws an exception.
   */
  @Override
  public T openConnection() {
    throw new SlcwException("Can't open connection from the abstract class.");
  }
}
