package com.keylane.mock.method;

import com.keylane.mock.url.MockUrl;

import java.util.Map;

public class MockMethodCaller_TestMethods {

    public final static Object NO_PARAMETER_RESPONSE = new Object();

    @MockUrl("/no-parameters")
    public Object noParameters() {
        return NO_PARAMETER_RESPONSE;
    }

    @MockUrl("/headers")
    public Map<String, String> headers(@MockParameter(MockParameterType.HEADERS) Map<String, String> headers) {
        return headers;
    }

    @MockUrl("/parameters")
    public Map<String, String> parameters(@MockParameter(MockParameterType.PARAMETERS) Map<String, String> parameters) {
        return parameters;
    }

    @MockUrl("/body")
    public String body(@MockParameter(MockParameterType.BODY) String body) {
        return body;
    }

    @MockUrl("/url")
    public String url(@MockParameter(MockParameterType.URL) String url) {
        return url;
    }

    @MockUrl("/verb")
    public String verb(@MockParameter(MockParameterType.VERB) String verb) {
        return verb;
    }

    @MockUrl("/no-annotation")
    public void noAnnotation(String parameterWithoutAnnotation) {

    }
}
