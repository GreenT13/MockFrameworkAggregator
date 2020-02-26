package com.keylane.mock.url;

import com.keylane.mock.url.test_container_url_is_prefixed.MockUrlContainer_ContainerUrlWithSlashTest;
import com.keylane.mock.url.test_container_url_is_prefixed.MockUrlContainer_ContainerUrlWithoutSlashTest;
import com.keylane.mock.url.test_matched_url_is_found.MockUrlContainer_MatchedUrlIsFound;
import com.keylane.mock.url.test_prefixed_url.MockUrlContainer_PrefixedUrl;
import com.keylane.mock.url.test_url_is_used_before_matcher.MockUrlContainer_UrlIsUsedBeforeMatcher;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class MockUrlConfigurationTest {
    private final static String THIS_PACKAGE = "com.keylane.mock.url.";

    @Test
    public void urlCanBePrefixedWithAndWithoutSlash() throws NoSuchMethodException {
        // Given
        MockUrlConfiguration mockUrlConfiguration = new MockUrlConfiguration(THIS_PACKAGE + "test_prefixed_url");

        // When
        Method method_with = mockUrlConfiguration.getMethodForUrl(MockUrlContainer_PrefixedUrl.WITH_SLASH);
        Method method_without = mockUrlConfiguration.getMethodForUrl("/" + MockUrlContainer_PrefixedUrl.WITHOUT_SLASH);

        // Then
        assertEquals(MockUrlContainer_PrefixedUrl.class.getMethod("withSlash"), method_with);
        assertEquals(MockUrlContainer_PrefixedUrl.class.getMethod("withoutSlash"), method_without);
    }

    @Test
    public void doubleConfiguredUrlThrowsException() {
        assertThrows(RuntimeException.class, () -> new MockUrlConfiguration(THIS_PACKAGE + "test_configured_twice"));
    }

    @Test
    public void mockContainerUrlIsPrefixed() throws NoSuchMethodException {
        // Given
        MockUrlConfiguration mockUrlConfiguration = new MockUrlConfiguration(THIS_PACKAGE + "test_container_url_is_prefixed");

        // When
        Method prefix_with_method_with = mockUrlConfiguration.getMethodForUrl(MockUrlContainer_ContainerUrlWithSlashTest.PREFIX_WITH_SLASH + MockUrlContainer_ContainerUrlWithSlashTest.WITH_SLASH);
        Method prefix_with_method_without = mockUrlConfiguration.getMethodForUrl(MockUrlContainer_ContainerUrlWithSlashTest.PREFIX_WITH_SLASH + "/" + MockUrlContainer_ContainerUrlWithSlashTest.WITHOUT_SLASH);
        Method prefix_without_method_with = mockUrlConfiguration.getMethodForUrl("/" + MockUrlContainer_ContainerUrlWithoutSlashTest.PREFIX_WITHOUT_SLASH + MockUrlContainer_ContainerUrlWithoutSlashTest.WITH_SLASH);
        Method prefix_without_method_without = mockUrlConfiguration.getMethodForUrl("/" + MockUrlContainer_ContainerUrlWithoutSlashTest.PREFIX_WITHOUT_SLASH + "/" + MockUrlContainer_ContainerUrlWithoutSlashTest.WITHOUT_SLASH);

        // Then
        assertEquals(MockUrlContainer_ContainerUrlWithSlashTest.class.getMethod("withSlash"), prefix_with_method_with);
        assertEquals(MockUrlContainer_ContainerUrlWithSlashTest.class.getMethod("withoutSlash"), prefix_with_method_without);
        assertEquals(MockUrlContainer_ContainerUrlWithoutSlashTest.class.getMethod("withSlash"), prefix_without_method_with);
        assertEquals(MockUrlContainer_ContainerUrlWithoutSlashTest.class.getMethod("withoutSlash"), prefix_without_method_without);
    }

    @Test
    public void matchedUrlIsFound() throws NoSuchMethodException {
        // Given
        MockUrlConfiguration mockUrlConfiguration = new MockUrlConfiguration(THIS_PACKAGE + "test_matched_url_is_found");

        // When
        Method method = mockUrlConfiguration.getMethodForUrl(MockUrlContainer_MatchedUrlIsFound.URL);

        // Then
        assertEquals(MockUrlContainer_MatchedUrlIsFound.class.getMethod("method"), method);
    }

    @Test
    public void urlIsUsedBeforeMatcher() throws NoSuchMethodException {
        // Given
        MockUrlConfiguration mockUrlConfiguration = new MockUrlConfiguration(THIS_PACKAGE + "test_url_is_used_before_matcher");

        // When
        Method method = mockUrlConfiguration.getMethodForUrl(MockUrlContainer_UrlIsUsedBeforeMatcher.URL);

        // Then
        assertEquals(MockUrlContainer_UrlIsUsedBeforeMatcher.class.getMethod("url"), method);
    }

    @Test
    public void doubleConfiguredMatcherThrowsException() {
        assertThrows(RuntimeException.class, () -> new MockUrlConfiguration(THIS_PACKAGE + "test_matcher_configured_twice"));
    }

    @Test
    public void incorrectReturnTypeThrowsException() {
        assertThrows(RuntimeException.class, () -> new MockUrlConfiguration(THIS_PACKAGE + "test_return_type"));
        assertThrows(RuntimeException.class, () -> new MockUrlConfiguration(THIS_PACKAGE + "test_return_type_matcher"));
    }

    @Test
    public void noMethodFoundReturnsNull() {
        // Given
        // Note that we must give a package that exists, so we use a random test case.
        MockUrlConfiguration mockUrlConfiguration = new MockUrlConfiguration(THIS_PACKAGE + "test_prefixed_url");

        // When
        Method method = mockUrlConfiguration.getMethodForUrl("not-existing-url");

        // Then
        assertNull(method);
    }
}
