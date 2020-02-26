package com.keylane.mock.method;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MockMethodCallerTest {
    private MockMethodCaller mockMethodCaller = new MockMethodCaller();

    @Test
    public void methodWithoutParametersIsInvoked() throws Exception {
        // Given
        Method method = MockMethodCaller_TestMethods.class.getMethod("noParameters");
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        // When
        Object response = mockMethodCaller.callMethod(method, httpServletRequest);

        // Then
        assertEquals(MockMethodCaller_TestMethods.NO_PARAMETER_RESPONSE, response);
    }

    @Test
    public void headersArePassedAsParameter() throws Exception {
        // Given
        Method method = MockMethodCaller_TestMethods.class.getMethod("headers", Map.class);
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        String headerName = "name";
        String headerValue = "value";
        Mockito.when(httpServletRequest.getHeaderNames()).thenReturn(Collections.enumeration(Collections.singletonList(headerName)));
        Mockito.when(httpServletRequest.getHeader(headerName)).thenReturn(headerValue);

        // When
        //noinspection unchecked
        Map<String, String> response = (Map<String, String>) mockMethodCaller.callMethod(method, httpServletRequest);

        // Then
        assertEquals(headerValue, response.get(headerName));

    }

    @Test
    public void parametersArePassedAsParameter() throws Exception {
        // Given
        Method method = MockMethodCaller_TestMethods.class.getMethod("parameters", Map.class);
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        String parameterName = "name";
        String parameterValue = "value";
        Mockito.when(httpServletRequest.getParameterNames()).thenReturn(Collections.enumeration(Collections.singletonList(parameterName)));
        Mockito.when(httpServletRequest.getParameter(parameterName)).thenReturn(parameterValue);

        // When
        //noinspection unchecked
        Map<String, String> response = (Map<String, String>) mockMethodCaller.callMethod(method, httpServletRequest);

        // Then
        assertEquals(parameterValue, response.get(parameterName));
    }

    @Test
    public void bodyIsPassedAsParameter() throws Exception {
        // Given
        Method method = MockMethodCaller_TestMethods.class.getMethod("body", String.class);
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        String body = "some-body-text";
        Mockito.when(httpServletRequest.getReader()).thenReturn(new BufferedReader(new StringReader(body)));

        // When
        String response = (String) mockMethodCaller.callMethod(method, httpServletRequest);

        // Then
        assertEquals(body, response);
    }

    @Test
    public void urlIsPassedAsParameter() throws Exception {
        // Given
        Method method = MockMethodCaller_TestMethods.class.getMethod("url", String.class);
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        String url = "/url";
        Mockito.when(httpServletRequest.getPathInfo()).thenReturn(url);

        // When
        String response = (String) mockMethodCaller.callMethod(method, httpServletRequest);

        // Then
        assertEquals(url, response);
    }

    @Test
    public void verbIsPassedAsParameter() throws Exception {
        // Given
        Method method = MockMethodCaller_TestMethods.class.getMethod("verb", String.class);
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);
        String verb = "POST";
        Mockito.when(httpServletRequest.getMethod()).thenReturn(verb);

        // When
        String response = (String) mockMethodCaller.callMethod(method, httpServletRequest);

        // Then
        assertEquals(verb, response);
    }

    @Test
    public void parameterWithoutAnnotationThrowsException() throws Exception {
        // Given
        Method method = MockMethodCaller_TestMethods.class.getMethod("noAnnotation", String.class);
        HttpServletRequest httpServletRequest = Mockito.mock(HttpServletRequest.class);

        // When and then
        assertThrows(RuntimeException.class, () -> mockMethodCaller.callMethod(method, httpServletRequest));
    }

}
