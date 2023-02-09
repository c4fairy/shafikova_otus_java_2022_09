package exceptions;

public class NotEnoughMoneyInAtmException extends RuntimeException {
    private final static String message = "Недостаточно средств в банкомате";
    public NotEnoughMoneyInAtmException() {
        super(message);
    }
}
