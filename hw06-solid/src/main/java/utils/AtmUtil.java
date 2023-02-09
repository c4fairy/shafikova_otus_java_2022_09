package utils;

import dicts.BillValues;
import exceptions.NotEnoughMoneyInAtmException;
import impls.CellImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//получаем минимальное количество банкнот для выдачи
public class AtmUtil {
    public static Map<BillValues, Integer> getMinBills(Map<BillValues, CellImpl> currentCells, int amount) {
        Map<BillValues, Integer> cellOutcome = new HashMap<>();
        int val = 0;
        List<Integer> billValues = new ArrayList<>();
        for (BillValues cell: currentCells.keySet())
            billValues.add(cell.getValue());
        billValues.sort(Collections.reverseOrder());
        for (Integer billValue : billValues) {
            for (Map.Entry<BillValues, CellImpl> cell : currentCells.entrySet()) {
                if (billValue == cell.getKey().getValue()) {
                    int count = cell.getValue().getCount();
                    int withdrowCount = amount / cell.getKey().getValue();
                    while (count > 0 && withdrowCount > 0) {
                        val++;
                        cellOutcome.put(cell.getKey(), val);
                        count--;
                        withdrowCount--;
                        amount -= cell.getKey().getValue();
                    }
                    val = 0;
                }
            }
        }
        if (amount == 0)
            return cellOutcome;
        else throw new NotEnoughMoneyInAtmException();
    }

}
