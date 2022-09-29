package com.zextras.persistence.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ObjectClass annotation is used to define the special for LDAP property you want to be mapped.
 * @ObjectClass annotation should be declared at the field level.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectClass {

  String name() default "objectClass";

  boolean binary() default false;
}
