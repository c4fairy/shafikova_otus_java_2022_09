package utils;

import dicts.BillValues;
import impls.CellImpl;

import java.util.Map;

public class Validation {
    //можем ли выдать запрошенную сумму денег
    public static boolean isAmountAllowed(Map<BillValues, Integer> outcome, Map<BillValues, CellImpl> currentCells) {
        boolean result = true;
        for (Map.Entry<BillValues, Integer> entryOutcome : outcome.entrySet()) {
            for (Map.Entry<BillValues, CellImpl> entryCells : currentCells.entrySet()) {
                if (entryOutcome.getKey() == entryCells.getKey() && entryOutcome.getValue() > entryCells.getValue().getCount() || entryOutcome.getValue() < 0){
                    result = false;
                return result; }
            }
        }
        return result;
    }
}
