package com.keylane.mock.response;

import java.util.Map;

public class Response {
    private final Map<String, String> headers;
    private final Integer status;
    private final String contentType;
    private final String body;

    public Response(Map<String, String> headers, Integer status, String contentType, String body) {
        this.headers = headers;
        this.status = status;
        this.contentType = contentType;
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Integer getStatus() {
        return status;
    }

    public String getContentType() {
        return contentType;
    }

    public String getBody() {
        return body;
    }
}
