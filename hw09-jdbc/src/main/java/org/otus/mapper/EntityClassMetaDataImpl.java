package org.otus.mapper;

import org.otus.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final String name;
    private final Field[] fields;
    private final Constructor<T> constructor;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.fields = clazz.getDeclaredFields();
        this.name = clazz.getSimpleName();
        try {
            this.constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getName() {
        return this.name.toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(this.fields)
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(this.fields);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(this.fields)
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .toList();
    }
}
