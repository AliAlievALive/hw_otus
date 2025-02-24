package org.otus.appcontainer;

import java.lang.reflect.Method;
import java.util.*;
import org.otus.appcontainer.api.AppComponent;
import org.otus.appcontainer.api.AppComponentsContainer;
import org.otus.appcontainer.api.AppComponentsContainerConfig;
import org.otus.exceptions.CustomException;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        List<Method> sortedBeanFactories = Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparing(
                        method -> method.getAnnotation(AppComponent.class).order()))
                .toList();
        processMethods(sortedBeanFactories);
    }

    private void processMethods(List<Method> sortedBeanFactories) {
        sortedBeanFactories.forEach(method -> {
            try {
                List<Object> args = new ArrayList<>();
                for (Class<?> parameterType : method.getParameterTypes()) {
                    Object appComponent = getAppComponent(parameterType);
                    args.add(appComponent);
                }
                Object bean = method.invoke(
                        method.getDeclaringClass().getDeclaredConstructor().newInstance(), args.toArray());
                String beanName = method.getAnnotation(AppComponent.class).name();
                Object oldBean = appComponentsByName.put(beanName, bean);
                if (oldBean != null) {
                    throw new CustomException("Bean name conflict for " + beanName);
                }
                appComponents.add(bean);
            } catch (Exception e) {
                throw new CustomException(String.format("Error creating bean: %s", e.getMessage()));
            }
        });
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> matchingComponents = appComponents.stream()
                .filter(component -> componentClass.isAssignableFrom(component.getClass()))
                .toList();

        if (matchingComponents.isEmpty()) {
            throw new CustomException("Component not found for type: " + componentClass.getName());
        }
        if (matchingComponents.size() > 1) {
            throw new CustomException("Multiple components found for type: " + componentClass.getName());
        }

        return (C) matchingComponents.getFirst();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        C component = (C) appComponentsByName.get(componentName);
        if (component == null) {
            throw new CustomException("Component not found for name: " + componentName);
        }
        return component;
    }
}
