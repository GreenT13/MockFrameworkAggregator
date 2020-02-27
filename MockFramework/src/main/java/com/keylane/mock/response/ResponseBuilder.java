package com.keylane.mock.response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Builder to create a {@link Response} object. Start with the builder using {@link #start()}. The object will be created
 * with the {@link #create()} function. An example:
 * <pre>{@code ResponseBuilder.start()
 *                 .setBody("hello world")
 *                 .setStatus(javax.servlet.http.HttpServletResponse.SC_OK)
 *                 .create()
 * }</pre>
 */
public class ResponseBuilder {
    private final static Logger log = LogManager.getLogger(ResponseBuilder.class);

    private Map<String, String> headers;
    private Integer status;
    private String contentType;
    private String body;

    private ResponseBuilder() {
        headers = new HashMap<>();
    }

    /**
     * Returns an instance of the ResponseBuilder.
     */
    public static ResponseBuilder start() {
        return new ResponseBuilder();
    }

    /**
     * Set the status of the response. Use integers defined in {@link javax.servlet.http.HttpServletResponse}.
     * @param status The status code.
     * @return {@link ResponseBuilder} object.
     */
    public ResponseBuilder setStatus(Integer status) {
        this.status = status;
        return this;
    }

    /**
     * Sets the body of the response.
     * @param body The body.
     * @return {@link ResponseBuilder} object.
     */
    public ResponseBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    /**
     * Fill the body with the content of the file at the file path.
     * @param filePath The path to the file, relative to the resources directory.
     * @return {@link ResponseBuilder} object.
     */
    public ResponseBuilder fillBodyWithFileContent(String filePath) {
        try {
            body = IOUtils.toString(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream(filePath)), StandardCharsets.UTF_8);
        } catch (IOException | NullPointerException e) {
            log.error("Failed to create body from file.", e);
            throw new RuntimeException("Could not find file '" + filePath + "'.", e);
        }
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
