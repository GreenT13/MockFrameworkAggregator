package com.keylane.mock.url.test_configured_twice;

import com.keylane.mock.response.Response;
import com.keylane.mock.url.MockUrl;

public class MockUrlContainer_ConfiguredTwiceTest {
    @MockUrl("/url")
    public Response method1() {
        return null;
    }

    @MockUrl("/url")
    public Response method2() {
        return null;
    }
}
