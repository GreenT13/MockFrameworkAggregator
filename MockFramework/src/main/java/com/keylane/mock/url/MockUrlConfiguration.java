package com.keylane.mock.url;

import com.keylane.mock.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public enum MockUrlConfiguration {
    INSTANCE;

    private HashMap<String, Method> urlMethodMap;
    private HashMap<Pattern, Method> urlMatcherMethodMap;

    MockUrlConfiguration() {
        initializeUrlMethodMap();
        initializeUrlMatcherMethodMap();
    }

    private void initializeUrlMethodMap() {
        urlMethodMap = new HashMap<>();

        Reflections reflections = new Reflections("", new MethodAnnotationsScanner());
        Set<Method> mockUrlMethods = reflections.getMethodsAnnotatedWith(MockUrl.class);
        for (Method method : mockUrlMethods) {
            if (!method.getReturnType().equals(Response.class)) {
                throw new RuntimeException("Return time of " + method.getDeclaringClass().getCanonicalName() + "." + method.getName()
                        + " does not return type Response.");
            }

            MockUrl mockUrl = method.getAnnotation(MockUrl.class);
            MockUrlContainer mockUrlContainer = method.getDeclaringClass().getAnnotation(MockUrlContainer.class);
            String url = mockUrl.value();

            if (mockUrlContainer != null && !StringUtils.isBlank(mockUrlContainer.value())) {
                String prefix = mockUrlContainer.value();
                if (!prefix.endsWith("/")) {
                    prefix = prefix + "/";
                }

                url = prefix + url;
            }

            if (!url.startsWith("/")) {
                url = "/" + url;
            }

            if (urlMethodMap.containsKey(url)) {
                throw new RuntimeException("Url '" + url + "' is configured twice.");
            }

            urlMethodMap.put(url, method);
        }
    }

    private void initializeUrlMatcherMethodMap() {
        urlMatcherMethodMap = new HashMap<>();

        Reflections reflections = new Reflections("", new MethodAnnotationsScanner());
        Set<Method> mockUrlMethods = reflections.getMethodsAnnotatedWith(MockUrlMatcher.class);
        for (Method method : mockUrlMethods) {
            if (!method.getReturnType().equals(Response.class)) {
                throw new RuntimeException("Return time of " + method.getDeclaringClass().getCanonicalName() + "." + method.getName()
                        + " does not return type Response.");
            }

            MockUrlMatcher mockUrlMatcher = method.getAnnotation(MockUrlMatcher.class);
            Pattern pattern = Pattern.compile(mockUrlMatcher.value());
            if (urlMatcherMethodMap.containsKey(pattern)) {
                throw new RuntimeException("Url matcher '" + mockUrlMatcher.value() + "' is configured twice.");
            }

            urlMatcherMethodMap.put(pattern, method);
        }
    }

    public Method getMethodForUrl(String url) {
        for (Map.Entry<Pattern, Method> entry : urlMatcherMethodMap.entrySet()) {
            if (entry.getKey().matcher(url).matches()) {
                return entry.getValue();
            }
        }

        return urlMethodMap.get(url);
    }
}
