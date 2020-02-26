package com.keylane.mock.response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ResponseFiller {
    public static void fillHttpServletResponseWithResponse(HttpServletResponse httpServletResponse, Response response) throws IOException {
        if (response.getBody() != null) {
            httpServletResponse.getWriter().write(response.getBody());
        }

        if (response.getContentType() != null) {
            httpServletResponse.setContentType(response.getContentType());
        }

        if (response.getHeaders() != null) {
            for (Map.Entry<String, String> entry : response.getHeaders().entrySet()) {
                httpServletResponse.addHeader(entry.getKey(), entry.getValue());
            }
        }

        if (response.getStatus() != null) {
            httpServletResponse.setStatus(response.getStatus());
        }
    }
}
