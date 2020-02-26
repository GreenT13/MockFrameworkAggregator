package com.keylane.mock.response;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseBuilderTest {

    @Test
    public void fieldsAreCorrectlyFilled() {
        // Given
        Integer status = HttpServletResponse.SC_OK;
        String body = "body";
        String contentType = "application/json";
        String headerKey = "key";
        String headerValue = "value";

        // When
        Response response = ResponseBuilder.start()
                .setStatus(status)
                .setBody(body)
                .setContentType(contentType)
                .addHeader("key", "value")
                .create();

        // Then
        assertEquals(status, response.getStatus());
        assertEquals(body, response.getBody());
        assertEquals(contentType, response.getContentType());
        assertEquals(headerValue, response.getHeaders().get(headerKey));
    }
}
