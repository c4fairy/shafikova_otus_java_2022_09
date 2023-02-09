import dicts.BillValues;
import impls.AtmImpl;
import interfaces.Atm;

import java.util.HashMap;
import java.util.Map;

public class AtmUser {
    public static void main(String[] args) {
        Atm atm = new AtmImpl();
        Map<BillValues, Integer> deposit = new HashMap<>();
        deposit.put(BillValues.ONE_HUNDRED, 2);
        deposit.put(BillValues.FIVE_HUNDREDS, 20);
        atm.depositMoney(deposit);

        System.out.println("Balance = " + atm.getBalance());
        atm.withdrawMoney(600);
        System.out.println("Balance = " + atm.getBalance());
        atm.withdrawMoney(20000);
    }
}
