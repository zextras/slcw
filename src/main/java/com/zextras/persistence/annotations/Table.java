package com.zextras.persistence.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Table annotation allows you to define the table, catalog, and schema names for your entity
 * mapping.
 * @Table annotation should be declared at the class level.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

  String name();

  String property() default "";
}
