package com.keylane.mock.url.test_url_is_used_before_matcher;

import com.keylane.mock.response.Response;
import com.keylane.mock.url.MockUrl;
import com.keylane.mock.url.MockUrlMatcher;

public class MockUrlContainer_UrlIsUsedBeforeMatcher {

    public final static String URL = "/random";
    @MockUrl(URL)
    public Response url() {
        return null;
    }

    @MockUrlMatcher(".*")
    public Response matchAny() {
        return null;
    }
}
