package com.keylane.mock.method;

import com.keylane.mock.MockServlet;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum MockParameterType {
    HEADERS(MockParameterType::determineHeaders),
    PARAMETERS(MockParameterType::determineParameters),
    BODY(MockParameterType::determineBody),
    URL(MockServlet::determinePathUrl),
    VERB(MockParameterType::determineHttpVerb);

    private Function<HttpServletRequest, Object> determineValue;

    MockParameterType(Function<HttpServletRequest, Object> determineValue) {
        this.determineValue = determineValue;
    }

    public Function<HttpServletRequest, Object> getFunctionToDetermineValue() {
        return determineValue;
    }

    private static Map<String, String> determineHeaders(HttpServletRequest httpServletRequest) {
        Map<String, String> headers = new HashMap<>();

        for (String headerName : Collections.list(httpServletRequest.getHeaderNames())) {
            headers.put(headerName, httpServletRequest.getHeader(headerName));
        }

        return headers;
    }

    private static Map<String, String> determineParameters(HttpServletRequest httpServletRequest) {
        Map<String, String> parameters = new HashMap<>();

        for (String parameterName : Collections.list(httpServletRequest.getParameterNames())) {
            parameters.put(parameterName, httpServletRequest.getParameter(parameterName));
        }

        return parameters;
    }

    private static String determineBody(HttpServletRequest httpServletRequest) {
        try {
            return httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException("Could not read body.", e);
        }
    }

    private static String determineHttpVerb(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getMethod();
    }
}
