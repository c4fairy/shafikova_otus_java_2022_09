package ru.otus.jdbc.mapper.impl;

import ru.otus.annotations.Id;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final String name;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.name = this.setName(clazz);
        this.constructor = this.setConstructor(clazz);
        this.idField = this.setIdField(clazz);
        this.allFields = this.setAllFields(clazz);
        this.fieldsWithoutId = this.setFieldsWithoutId(clazz);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return this.constructor;
    }

    @Override
    public Field getIdField() {
        return this.idField;
    }

    @Override
    public List<Field> getAllFields() {
        return this.allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return this.fieldsWithoutId;
    }

    private String setName(Class<T> clazz) {
        return clazz.getSimpleName();
    }

    private Constructor<T> setConstructor(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Class<?>[] classes = Arrays.stream(fields)
                .map(Field::getType)
                .toArray(Class<?>[]::new);
        try {
            return clazz.getDeclaredConstructor(classes);
        } catch (NoSuchMethodException e) {
            throw new DataTemplateException(e);
        }
    }

    private Field setIdField(Class<T> clazz) {
        Optional<Field> field = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst();
        if (field.isEmpty()) {
            throw new RuntimeException("annotation not found in class");
        } else {
            return field.get();
        }
    }

    private List<Field> setAllFields(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).toList();
    }

    private List<Field> setFieldsWithoutId(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .toList();
    }
}
