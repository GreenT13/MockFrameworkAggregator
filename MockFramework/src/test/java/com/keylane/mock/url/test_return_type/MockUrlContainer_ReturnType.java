package com.keylane.mock.url.test_return_type;

import com.keylane.mock.url.MockUrl;

public class MockUrlContainer_ReturnType {

    @MockUrl("/random")
    public Integer incorrectReturnType() {
        return null;
    }
}
