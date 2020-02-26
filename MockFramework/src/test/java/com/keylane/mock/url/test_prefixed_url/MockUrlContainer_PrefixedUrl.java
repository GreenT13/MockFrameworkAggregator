package com.keylane.mock.url.test_prefixed_url;

import com.keylane.mock.response.Response;
import com.keylane.mock.url.MockUrl;

public class MockUrlContainer_PrefixedUrl {

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
