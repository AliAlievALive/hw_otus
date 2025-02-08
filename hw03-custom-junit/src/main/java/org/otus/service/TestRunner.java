package org.otus.service;

import static org.otus.util.ReflectionHelper.callMethod;
import static org.otus.util.ReflectionHelper.instantiate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.otus.annotations.After;
import org.otus.annotations.Before;
import org.otus.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"java:S112", "java:S1141"})
public class TestRunner {
    private static final Logger log = LoggerFactory.getLogger(TestRunner.class);

    @SuppressWarnings("java:S1118")
    public TestRunner() {
        throw new IllegalStateException("Utility class");
    }

    public static void runTests(String className) {
        try {
            Class<?> testClass = Class.forName("org.otus.tests." + className);

            var beforeMethods = initialFromAnnotations(testClass, Before.class);
            var testMethods = initialFromAnnotations(testClass, Test.class);
            var afterMethods = initialFromAnnotations(testClass, After.class);

            int passed = 0;
            int failed = 0;

            for (Method testMethod : testMethods) {
                Object testInstance = instantiate(testClass);
                try {
                    for (Method beforeMethod : beforeMethods) {
                        callMethod(testInstance, beforeMethod.getName());
                    }

                    callMethod(testInstance, testMethod.getName());
                    passed++;
                } catch (Exception e) {
                    log.error(String.format(
                            "Test %s failed: %s",
                            testMethod.getName(), e.getCause().getCause()));
                    failed++;
                } finally {
                    afterMethods(afterMethods, testInstance);
                }
            }

            log.info("Total tests: {} ", testMethods.size());
            log.info("Passed: {} ", passed);
            log.info("Failed: {}", failed);
        } catch (Exception e) {
            throw new RuntimeException("Failed to run tests", e);
        }
    }

    private static List<Method> initialFromAnnotations(Class<?> testClass, Class<? extends Annotation> method) {
        return Arrays.stream(testClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(method))
                .toList();
    }

    private static void afterMethods(List<Method> afterMethods, Object testInstance) {
        for (Method afterMethod : afterMethods) {
            try {
                callMethod(testInstance, afterMethod.getName());
            } catch (Exception e) {
                log.error(String.format("After method failed: %s ", e.getCause()));
            }
        }
    }
}
