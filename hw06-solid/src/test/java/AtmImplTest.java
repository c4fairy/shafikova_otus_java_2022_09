import dicts.BillValues;
import exceptions.NotEnoughMoneyInAtmException;
import impls.AtmImpl;
import interfaces.Atm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class AtmImplTest {
    @Test
    @DisplayName("Пополнение и получение баланса АТМ ")
    public void shouldAddBalanceAmountTest() {
        Atm atm = new AtmImpl();
        Map<BillValues, Integer> deposit = new HashMap<>();
        deposit.put(BillValues.ONE_HUNDRED, 2);
        deposit.put(BillValues.FIVE_HUNDREDS, 2);
        atm.depositMoney(deposit);
        Assertions.assertEquals(1200, atm.getBalance());
    }

    @Test
    @DisplayName("Снятие и получение баланса АТМ")
    public void shouldReduceBalanceAmountTest() {
        Atm atm = new AtmImpl();
        Map<BillValues, Integer> deposit = new HashMap<>();
        deposit.put(BillValues.ONE_HUNDRED, 2);
        deposit.put(BillValues.FIVE_HUNDREDS, 2);
        atm.depositMoney(deposit);
        atm.withdrawMoney(500);
        Assertions.assertEquals(700, atm.getBalance());
    }

    @Test
    @DisplayName("Снятие и получение баланса АТМ с исключением")
    public void shouldNotReduceBalanceAmountTest() {
        String message = "";
        Atm atm = new AtmImpl();
        Map<BillValues, Integer> deposit = new HashMap<>();
        deposit.put(BillValues.ONE_HUNDRED, 2);
        deposit.put(BillValues.FIVE_HUNDREDS, 2);
        atm.depositMoney(deposit);
        try {
            atm.withdrawMoney(5000);
        } catch (NotEnoughMoneyInAtmException e) {
            message = e.getMessage();
        }
        Assertions.assertEquals("Недостаточно средств в банкомате", message);
    }
}
