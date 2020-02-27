package com.keylane.mock.url;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that can be added to a class containing methods which are annotated with {@link MockUrl} or {@link MockUrlMatcher}.
 * If {@link #value()} is not blank, it will be used as a prefix for {@link MockUrl#value()}.
 * @implNote It is not required to start or end with a slash. This will automatically be added if missing.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockUrlContainer {
    String value() default "";
}
