package org.otus.mapper;

import org.otus.annotations.Id;
import org.otus.core.repository.DataTemplate;
import org.otus.core.repository.DataTemplateException;
import org.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        String selectByIdSql = entitySQLMetaData.getSelectByIdSql();

        return dbExecutor.executeSelect(
                connection,
                selectByIdSql,
                List.of(id),
                rs -> {
                    try {
                        if (rs.next()) {
                            return getInstance(rs);
                        }
                    } catch (Exception e) {
                        throw new DataTemplateException(e);
                    }
                    return null;
                }
        );

    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(
                        connection,
                        entitySQLMetaData.getSelectAllSql(),
                        Collections.emptyList(),
                        rs -> {
                            try {
                                List<T> result = new ArrayList<>();
                                while (rs.next()) {
                                    T instance = getInstance(rs);
                                    result.add(instance);
                                }
                                return result;
                            } catch (Exception e) {
                                throw new DataTemplateException(e);
                            }
                        }
                )
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        var fields = Arrays.stream(client.getClass().getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .toList();
        var params = fields.stream()
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(client);
                    } catch (IllegalAccessException e) {
                        throw new DataTemplateException(e);
                    }
                })
                .toList();
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
    }

    @Override
    public void update(Connection connection, T client) {
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), List.of(client));
    }

    private T getInstance(ResultSet rs) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SQLException {
        EntityClassMetaData<T> entityClassMetaData = (EntityClassMetaData<T>) entitySQLMetaData.getEntityClassMetaData();
        T instance = entityClassMetaData.getConstructor().newInstance();

        for (Field field : entityClassMetaData.getAllFields()) {
            field.setAccessible(true);
            Object value = rs.getObject(field.getName(), field.getType());
            field.set(instance, value);
        }
        return instance;
    }
}
