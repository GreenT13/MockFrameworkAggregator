package com.keylane.mock.response;

import java.util.Map;

/**
 * Required return object of methods annotated with {@link com.keylane.mock.url.MockUrl} and {@link com.keylane.mock.url.MockUrlMatcher}.
 * The response object will be used to fill a {@link javax.servlet.http.HttpServletResponse} by {@link ResponseFiller}.
 */
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

    @Override
    public String toString() {
        return "Response{" +
                "headers=" + headers +
                ", status=" + status +
                ", contentType='" + contentType + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
