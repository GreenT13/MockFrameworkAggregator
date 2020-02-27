package com.keylane.mock.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class for invoking methods that have been registered by {@link com.keylane.mock.url.MockUrlConfiguration}.
 */
public class MockMethodCaller {
    private final static Logger log = LogManager.getLogger(MockMethodCaller.class);

    /**
     * Invoke the given method. All the arguments will be dynamically filled in based on the given httpServletRequest.
     * @param method             The method.
     * @param httpServletRequest The request.
     * @return The return object of the invoked method.
     */
    public Object invokeMethod(Method method, HttpServletRequest httpServletRequest) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Object> args = new ArrayList<>();
        for (Parameter parameter : method.getParameters()) {
            MockParameterType mockParameterType = determineMockParameterType(parameter);
            args.add(mockParameterType.getFunctionToDetermineValue().apply(httpServletRequest));

            log.debug("Added parameter {} with value {}.", mockParameterType.name(), args.get(args.size() - 1).toString());
        }

        Object mockUrlContainer = method.getDeclaringClass().getDeclaredConstructor().newInstance();
        return method.invoke(mockUrlContainer, args.toArray());
    }

    /**
     * Determine the {@link MockParameterType} of the given parameter.
     * @param parameter The parameter.
     */
    private MockParameterType determineMockParameterType(Parameter parameter) {
        if (parameter.isAnnotationPresent(MockParameter.class)) {
            return parameter.getAnnotation(MockParameter.class).value();
        }

        throw new RuntimeException("Method contains a parameter without the MockParameter annotation.");
    }
}
