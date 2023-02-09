package ru.otus.processor;

import ru.otus.model.Message;

//Меняет местами значения field11 и field12
public class ProcessorChangeFields implements Processor{
    @Override
    public Message process(Message message) {
        String buffField = message.getField11();
        return message.toBuilder().field11(message.getField12()).field12(buffField).build();
    }
}
