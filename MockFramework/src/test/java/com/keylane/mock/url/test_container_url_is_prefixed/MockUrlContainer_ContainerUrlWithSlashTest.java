package com.keylane.mock.url.test_container_url_is_prefixed;

import com.keylane.mock.response.Response;
import com.keylane.mock.url.MockUrl;
import com.keylane.mock.url.MockUrlContainer;

@MockUrlContainer(MockUrlContainer_ContainerUrlWithSlashTest.PREFIX_WITH_SLASH)
public class MockUrlContainer_ContainerUrlWithSlashTest {
    public final static String PREFIX_WITH_SLASH = "/prefix-with-slash";

    public final static String WITH_SLASH = "/with_slash";
    @MockUrl(WITH_SLASH)
    public Response withSlash() {
        return null;
    }

    public final static String WITHOUT_SLASH = "without-slash";
    @MockUrl(WITHOUT_SLASH)
    public Response withoutSlash() {
        return null;
    }
}
