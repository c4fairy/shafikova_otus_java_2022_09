package interfaces;

import dicts.BillValues;

import java.util.Map;

public interface Atm {
    void depositMoney(Map<BillValues, Integer> income);

    void withdrawMoney(int amount);

    int getBalance();
}
