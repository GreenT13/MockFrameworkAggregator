package com.keylane.mock;

import com.keylane.mock.method.MockMethodCaller;
import com.keylane.mock.response.Response;
import com.keylane.mock.response.ResponseFiller;
import com.keylane.mock.url.MockMethodFinder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet which finds the right mock method to call based on the url, calls it and returns the response.
 */
public class MockServlet extends HttpServlet {
    private final static Logger log = LogManager.getLogger(MockServlet.class);

    @Override
    public void init() {
        // Call method to force initialization of the class.
        MockMethodFinder.INSTANCE.getMethodForUrl("");
    }

    /**
     * Return the url that is called, without the base and context. It always starts with a slash.
     *
     * @param httpServletRequest The request.
     */
    public static String determinePathUrl(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getPathInfo();
    }

    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String url = determinePathUrl(httpServletRequest);
        log.debug("Received request on url '{}' with verb {}.", url, httpServletRequest.getMethod());
        Method method = MockMethodFinder.INSTANCE.getMethodForUrl(url);

        // In the case that no method is found, we return a 404.
        if (method == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            httpServletResponse.getWriter().print("Could not find any mock configured on url '" + url + "'.");
            log.error("Could not find request for url '{}'.", url);
            return;
        }

        MockMethodCaller mockMethodCaller = new MockMethodCaller();
        Response response;
        try {
            response = (Response) mockMethodCaller.invokeMethod(method, httpServletRequest);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ServletException("Could not call method " + method.getDeclaringClass().getName() + "#" + method.getName(),e);
        }

        log.debug("Method returned: {}", response.toString());
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
