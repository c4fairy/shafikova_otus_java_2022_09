package ru.otus.exceptions;

public class TooManyComponentsException extends RuntimeException {
    private static final String MESSAGE_TEMPLATE = "More than once components compatible with required class %s";
    private static final long serialVersionUID = -7326887704852891722L;

    public TooManyComponentsException(Class<?> clazz) {
        super(MESSAGE_TEMPLATE.formatted(clazz.getCanonicalName()));
    }
}
