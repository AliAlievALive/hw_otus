package org.otus.dto;

import org.otus.annotations.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {
    }

    public static TestLoggingInterface createMyClass() {
        TestLoggingInterface target = new TestLogging();

        Map<Method, Boolean> annotatedMethods = new HashMap<>();
        for (Method method : target.getClass().getMethods()) {
            annotatedMethods.put(method, method.isAnnotationPresent(Log.class));
        }

        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class},
                (proxy, method, args) -> {
                    if (Boolean.TRUE.equals(annotatedMethods.get(method))) {
                        logger.info("executed method:{}, calculation, param:{}", method.getName(), Arrays.toString(args));
                    }
                    return method.invoke(target, args);
                });
    }
}
