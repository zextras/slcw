package com.zextras.persistence.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Enity annotation is used to declare a class as an entity (i.e. a persistent POJO class).
 * @Enity annotation should be declared at the class level.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {

}
