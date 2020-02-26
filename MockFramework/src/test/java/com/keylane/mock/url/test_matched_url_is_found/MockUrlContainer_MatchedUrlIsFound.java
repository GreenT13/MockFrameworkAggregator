package com.keylane.mock.url.test_matched_url_is_found;

import com.keylane.mock.response.Response;
import com.keylane.mock.url.MockUrl;
import com.keylane.mock.url.MockUrlMatcher;

public class MockUrlContainer_MatchedUrlIsFound {

    public final static String URL = "/random-something";

    @MockUrlMatcher("/random-.*")
    public Response method() {
        return null;
    }
}
