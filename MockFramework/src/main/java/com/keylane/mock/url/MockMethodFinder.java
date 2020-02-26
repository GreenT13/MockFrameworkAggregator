package com.keylane.mock.url;

import java.lang.reflect.Method;

public enum MockMethodFinder {
    INSTANCE;

    private MockUrlConfiguration mockUrlConfiguration;

    MockMethodFinder() {
        mockUrlConfiguration = new MockUrlConfiguration("");
    }

    public Method getMethodForUrl(String url) {
        return mockUrlConfiguration.getMethodForUrl(url);
    }
}
