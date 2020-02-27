package com.keylane.mock.url;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for configuring a mock method, where the method will be called if the incoming URL is equal to {@link #value()}.
 * @implNote It is not required to start with a slash. This will be automatically added if needed.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockUrl {
    /**
     * Returns the string which will be used to check against incoming URL's.
     */
    String value();
}
