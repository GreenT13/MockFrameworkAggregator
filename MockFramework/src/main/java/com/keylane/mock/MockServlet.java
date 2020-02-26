package com.keylane.mock;

import com.keylane.mock.method.MockUrlMethodCaller;
import com.keylane.mock.response.Response;
import com.keylane.mock.response.ResponseFiller;
import com.keylane.mock.url.MockUrlConfiguration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MockServlet extends HttpServlet {

    public static String determinePathUrl(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getPathInfo();
    }

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String url = determinePathUrl(httpServletRequest);
        Method method = MockUrlConfiguration.INSTANCE.getMethodForUrl(url);

        // In the case that no method is found, we return a 404.
        if (method == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            httpServletResponse.getWriter().print("Could not find any mock configured on url '" + url + "'.");
            return;
        }

        MockUrlMethodCaller mockUrlMethodCaller = new MockUrlMethodCaller();
        Response response;
        try {
            response = mockUrlMethodCaller.callMethod(method, httpServletRequest);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ServletException(e);
        }

        ResponseFiller.fillHttpServletResponseWithResponse(httpServletResponse, response);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
