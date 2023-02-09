package dicts;

public enum BillValues {
    ONE_HUNDRED(100),
    TWO_HUNDREDS(200),
    FIVE_HUNDREDS(500),
    ONE_THOUSAND(1000),
    TWO_THOUSANDS(2000),
    FIVE_THOUSANDS(5000),
    ;
    private final int value;

    BillValues(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
