package com.keylane.mock.url.test_return_type_matcher;

import com.keylane.mock.url.MockUrl;
import com.keylane.mock.url.MockUrlMatcher;

public class MockUrlContainer_ReturnTypeMatcher {

    @MockUrlMatcher("/random")
    public Integer incorrectReturnType() {
        return null;
    }
}
