package com.keylane;

import com.keylane.mock.method.MockParameter;
import com.keylane.mock.method.MockParameterType;
import com.keylane.mock.response.Response;
import com.keylane.mock.response.ResponseBuilder;
import com.keylane.mock.url.MockUrl;
import com.keylane.mock.url.MockUrlMatcher;

import java.util.Map;

public class HelloWorld {

    @MockUrl("hello-world")
    public Response helloWorld() {
        return ResponseBuilder.start()
                .setBody("hello world")
                .create();
    }

    @MockUrl("hello-world-body")
    public Response helloWorldBody(@MockParameter(MockParameterType.BODY) String body) {
        return ResponseBuilder.start()
                .setBody("hello" + body + "world")
                .create();
    }

    @MockUrl("hello-world-header")
    public Response helloWorldHeader(@MockParameter(MockParameterType.HEADERS) Map<String, String> headers) {
        return createMapResponse(headers);
    }

    @MockUrl("hello-world-url")
    public Response helloWorldUrl(@MockParameter(MockParameterType.URL) String url) {
        return ResponseBuilder.start()
                .setBody(url)
                .create();
    }

    @MockUrl("hello-world-parameter")
    public Response helloWorldParameter(@MockParameter(MockParameterType.PARAMETERS) Map<String, String> parameters) {
        return createMapResponse(parameters);
    }

    @MockUrl("/hello-world-verb")
    public Response helloWorldParameter(@MockParameter(MockParameterType.VERB) String verb) {
        return ResponseBuilder.start()
                .setBody(verb)
                .create();
    }

    @MockUrlMatcher("/hello-world-.*-something")
    public Response helloWorldMatcher() {
        return ResponseBuilder.start()
                .setBody("You matched!")
                .create();
    }


    private Response createMapResponse(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        return ResponseBuilder.start()
                .setBody(stringBuilder.toString())
                .create();
    }

}
