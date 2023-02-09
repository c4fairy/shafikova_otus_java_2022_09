package impls;

import dicts.BillValues;
import exceptions.NotEnoughMoneyInAtmException;
import interfaces.CellService;
import utils.AtmUtil;
import utils.Validation;

import java.util.HashMap;
import java.util.Map;

//сервис для работы с ячейками АТМ
public class CellServiceImpl implements CellService {

    public Map<BillValues, CellImpl> currentCells = new HashMap<>();

    public CellServiceImpl(Map<BillValues, CellImpl> initCells) {
        for (Map.Entry<BillValues, CellImpl> entryInit : initCells.entrySet())
            this.currentCells.put(entryInit.getKey(), new CellImpl(entryInit.getValue().getCount()));
    }

    public CellServiceImpl() {
        this.currentCells.put(BillValues.FIVE_THOUSANDS, new CellImpl(0));
        this.currentCells.put(BillValues.TWO_THOUSANDS, new CellImpl(0));
        this.currentCells.put(BillValues.ONE_THOUSAND, new CellImpl(0));
        this.currentCells.put(BillValues.FIVE_HUNDREDS, new CellImpl(0));
        this.currentCells.put(BillValues.TWO_HUNDREDS, new CellImpl(0));
        this.currentCells.put(BillValues.ONE_HUNDRED, new CellImpl(0));
    }

    public void depositMoney(Map<BillValues, Integer> income) {
        for (Map.Entry<BillValues, Integer> entryIncome : income.entrySet()) {
            for (Map.Entry<BillValues, CellImpl> entryCells : currentCells.entrySet()) {
                if (entryIncome.getKey() == entryCells.getKey()) {
                    CellImpl cell = currentCells.get(entryCells.getKey());
                    cell.addBill(entryIncome.getValue());
                    currentCells.put(entryIncome.getKey(), cell);
                }
            }
        }
    }

    public void withdrawMoney(int amount) {
        Map<BillValues, Integer> outcome = AtmUtil.getMinBills(currentCells, amount);
        if (Validation.isAmountAllowed(outcome, currentCells)) {
            for (Map.Entry<BillValues, Integer> entryOutcome : outcome.entrySet()) {
                for (Map.Entry<BillValues, CellImpl> entryCells : currentCells.entrySet()) {
                    if (entryOutcome.getKey() == entryCells.getKey()) {
                        CellImpl cell = currentCells.get(entryCells.getKey());
                        cell.removeBill(entryOutcome.getValue());
                        currentCells.put(entryOutcome.getKey(), cell);
                    }
                }
            }
        } else throw new NotEnoughMoneyInAtmException();
    }

    public int getBalance() {
        int sum = 0;
        for (Map.Entry<BillValues, CellImpl> entryCells : currentCells.entrySet()) {
            sum += entryCells.getKey().getValue() * entryCells.getValue().getCount();
        }
        return sum;
    }
}
