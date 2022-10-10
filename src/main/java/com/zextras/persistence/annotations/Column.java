package com.zextras.persistence.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Column annotation is used to define the property you want to be mapped.
 * @Column annotation should be declared at the field level.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

  String name();

  boolean binary() default false;
}
