package interfaces;

import dicts.BillValues;

import java.util.Map;

public interface CellService {
    void depositMoney(Map<BillValues, Integer> income);

    void withdrawMoney(int amount);

    int getBalance();
}
