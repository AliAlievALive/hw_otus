package org.otus.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "select * from %s".formatted(entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return "select * from %s where %s = ?".formatted(entityClassMetaData.getName(), entityClassMetaData.getIdField().getName());
    }

    @Override
    public String getInsertSql() {
        var fields = entityClassMetaData.getFieldsWithoutId();
        String columns = fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        String placeholders = fields.stream()
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        return "insert into %s (%s) values (%s)".formatted(entityClassMetaData.getName(), columns, placeholders);
    }

    @Override
    public String getUpdateSql() {
        String setClause = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> field.getName() + " = ?")
                .collect(Collectors.joining(", "));
        return "update %s set %s where %s = ?".formatted(entityClassMetaData.getName(), setClause, entityClassMetaData.getIdField().getName());
    }

    @Override
    public EntityClassMetaData<Object> getEntityClassMetaData() {
        return (EntityClassMetaData<Object>) this.entityClassMetaData;
    }
}
