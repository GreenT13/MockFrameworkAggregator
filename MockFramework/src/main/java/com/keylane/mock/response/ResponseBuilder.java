package com.keylane.mock.response;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ResponseBuilder {
    private Map<String, String> headers;
    private Integer status;
    private String contentType;
    private String body;

    private ResponseBuilder() {
        headers = new HashMap<>();
    }

    public static ResponseBuilder start() {
        return new ResponseBuilder();
    }

    public ResponseBuilder setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public ResponseBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public ResponseBuilder addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public ResponseBuilder setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public Response create() {
        return new Response(headers, status, contentType, body);
    }
}
