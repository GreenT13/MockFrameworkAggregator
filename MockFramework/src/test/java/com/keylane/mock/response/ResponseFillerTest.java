package com.keylane.mock.response;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ResponseFillerTest {

    @Test
    public void noMethodsAreCalledWhenResponseIsEmpty() throws IOException {
        // Given
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        Response response = new Response(null, null, null, null);

        // When
        ResponseFiller.fillHttpServletResponseWithResponse(httpServletResponse, response);

        // Then
        Mockito.verifyNoInteractions(httpServletResponse);
    }

    @Test
    public void responseMethodsAreCalled() throws IOException {
        // Given
        HttpServletResponse httpServletResponse = Mockito.mock(HttpServletResponse.class);
        PrintWriter printWriter = Mockito.mock(PrintWriter.class);
        Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);

        Map<String, String> headers = new HashMap<>();
        headers.put("key", "value");
        Response response = new Response(headers, 200, "application/json", "body");

        // When
        ResponseFiller.fillHttpServletResponseWithResponse(httpServletResponse, response);

        // Then
        Mockito.verify(httpServletResponse).getWriter();
        Mockito.verify(printWriter).write("body");
        Mockito.verify(httpServletResponse).setStatus(200);
        Mockito.verify(httpServletResponse).setContentType("application/json");
        Mockito.verify(httpServletResponse).addHeader("key", "value");
    }
}
