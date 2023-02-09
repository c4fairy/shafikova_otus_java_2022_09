package ru.otus.core.sessionmanager;

public interface TransactionManager {

    <T> T doInTransaction(TransactionAction<T> action);
    //не приводит к попаданию изменений в бд
    <T> T doInReadOnlyTransaction(TransactionAction<T> action);
}
