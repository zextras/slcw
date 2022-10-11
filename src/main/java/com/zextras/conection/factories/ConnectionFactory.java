package com.zextras.conection.factories;

/**
 * A ConnectionFactory relies on a separate factory to actually execute the tasks.
 * <p></p>
 * All Known Implementing Classes: {@link LdapConnectionFactory}.
 */
public interface ConnectionFactory<T> {

  T openConnection();
}
