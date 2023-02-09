package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.LoggerProcessor;
import ru.otus.processor.ProcessorChangeFields;
import ru.otus.processor.ProcessorConcatFields;
import ru.otus.processor.ProcessorUpperField10;

import java.util.List;

public class HomeWork {
    public static void main(String[] args) {
        ObjectForMessage objForMessage = new ObjectForMessage();
        objForMessage.setData(List.of("field13"));
        var processors = List.of(new ProcessorConcatFields(),
                new LoggerProcessor(new ProcessorChangeFields()));

        var complexProcessor = new ComplexProcessor(processors, ex -> {
        });
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(objForMessage)
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(historyListener);
    }
}
