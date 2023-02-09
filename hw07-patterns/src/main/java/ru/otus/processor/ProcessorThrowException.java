package ru.otus.processor;

import ru.otus.exception.ProcessorEvenSecException;
import ru.otus.model.Message;

//Выбрасывает исключение в четную секунду
public class ProcessorThrowException implements Processor {
    //зависим не от конкретного класса, а от интерфейса
    private final DateTimeProvider dateTimeProvider;

    //применяем DI
    public ProcessorThrowException(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if (dateTimeProvider.getDate().getSecond() % 2 == 0)
            throw new ProcessorEvenSecException();
        return message;
    }
}
