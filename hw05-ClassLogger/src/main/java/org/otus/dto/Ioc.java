package org.otus.dto;

import org.otus.annotations.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class Ioc {
    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    private Ioc() {
    }

    public static TestLoggingInterface createMyClass() {
        TestLoggingInterface target = new TestLogging();

        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                        new Class<?>[]{TestLoggingInterface.class},
                        (proxy, method, args) -> {
                            Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
                            if (targetMethod.isAnnotationPresent(Log.class)) {
                                logger.info("executed method:{}, calculation, param:{}", method.getName(), Arrays.toString(args));
                            }
                            return method.invoke(target, args);
                        });
    }
}
