package com.keylane.mock.url;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for configuring a mock method, where the method will be called if the incoming URL matches (using RegExp) {@link #value()}.
 * @implNote The URL will always start with a slash, so the given {@link #value()} should as well.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockUrlMatcher {
    /**
     * Returns the RegExp string which will be used to match against incoming URL's.
     */
    String value();
}
