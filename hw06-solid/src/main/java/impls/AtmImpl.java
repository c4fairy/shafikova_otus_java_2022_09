package impls;

import dicts.BillValues;
import interfaces.Atm;
import interfaces.CellService;

import java.util.Map;

//банкомат
public class AtmImpl implements Atm {
    private final CellService cellService = new CellServiceImpl();

    //предполагаем, что сам АТМ предоставляет информацию о номинале и кол-ве входящих купюр
    public void depositMoney(Map<BillValues, Integer> income) {
        cellService.depositMoney(income);
    }

    public void withdrawMoney(int amount) {
        cellService.withdrawMoney(amount);
    }

    public int getBalance() {
        return cellService.getBalance();
    }
}
