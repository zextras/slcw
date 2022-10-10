package com.zextras.persistence.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Id annotation declares the identifier property of an entity.
 * @Id annotation should be declared at the field level.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {

  String name() default "";

  boolean binary() default false;
}
