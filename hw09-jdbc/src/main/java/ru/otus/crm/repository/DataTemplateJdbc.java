package ru.otus.crm.repository;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохраняет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor,
                            EntitySQLMetaData entitySQLMetaData,
                            EntityClassMetaData<T> entityClassMetaData
    ) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return this.entityClassMetaData.getConstructor().newInstance(this.getObjectByResultSet(rs));
                }
                return null;
            } catch (SQLException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new UnsupportedOperationException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var clientList = new ArrayList<T>();
            try {
                while (rs.next()) {
                    clientList.add(this.entityClassMetaData.getConstructor().newInstance(this.getObjectByResultSet(rs)));
                }
                return clientList;
            } catch (SQLException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
                throw new UnsupportedOperationException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        String sql = entitySQLMetaData.getInsertSql();
        try {
            return dbExecutor.executeStatement(connection, sql, this.getObject(client));
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        String sql = entitySQLMetaData.getUpdateSql();
        try {
            List<Object> objects = this.getObject(client);
            objects.add(client.getClass().getDeclaredMethod(this.getMethodName(entityClassMetaData.getIdField().getName()))
                    .invoke(client));
            dbExecutor.executeStatement(connection, sql, objects);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException(e);
        }
    }

    public Object[] getObjectByResultSet(ResultSet rs) throws SQLException {
        Object[] objects = new Object[entityClassMetaData.getAllFields().size()];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = rs.getObject(i + 1);
        }
        return objects;
    }

    public List<Object> getObject(T client)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        List<Object> objects = new ArrayList<>();
        for (Field field : fields) {
            objects.add(client.getClass().getDeclaredMethod(this.getMethodName(field.getName())).invoke(client));
        }
        return objects;
    }

    public String getMethodName(String name) {
        return "get".concat(name.substring(0, 1).toUpperCase()).concat(name.substring(1));
    }
}
