package ru.otus.exception;

public class ProcessorEvenSecException extends RuntimeException {
    private final static String message = "Наступила четная секунда";
    public ProcessorEvenSecException() {
        super(message);
    }
}
