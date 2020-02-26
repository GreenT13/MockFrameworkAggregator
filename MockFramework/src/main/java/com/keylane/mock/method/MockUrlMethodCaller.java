package com.keylane.mock.method;

import com.keylane.mock.response.Response;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * It is allowed to add the following parameters:
 * <ul>
 *     <li>{@code Map<String, String>} headers
 *     <li>{@code String} body
 *     <li>{@code String} url
 * </ul>
 */
public class MockUrlMethodCaller {
    // Possible parameters are: nothing or body as string.
    public Response callMethod(Method method, HttpServletRequest httpServletRequest) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Object> args = new ArrayList<>();
        for (Parameter parameter : method.getParameters()) {
            MockParameterType mockParameterType = determineMockParameterType(parameter);
            args.add(mockParameterType.getFunctionToDetermineValue().apply(httpServletRequest));
        }

        Object mockUrlContainer = method.getDeclaringClass().getDeclaredConstructor().newInstance();
        return (Response) method.invoke(mockUrlContainer, args.toArray());
    }

    private MockParameterType determineMockParameterType(Parameter parameter) {
        if (parameter.isAnnotationPresent(MockParameter.class)) {
            return parameter.getAnnotation(MockParameter.class).value();
        }

        throw new RuntimeException("Could not determine MockParameterType");
    }
}
