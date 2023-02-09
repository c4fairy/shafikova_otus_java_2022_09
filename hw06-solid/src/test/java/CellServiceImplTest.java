import dicts.BillValues;
import impls.CellServiceImpl;
import interfaces.CellService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class CellServiceImplTest {

    @Test
    @DisplayName("Добавление купюр в соответствующие ячейки и получение баланса")
    public void shouldAddAllBillsTest() {
        Map<BillValues, Integer> income = new HashMap<>();
        income.put(BillValues.ONE_THOUSAND, 7);
        income.put(BillValues.FIVE_THOUSANDS, 2);
        CellService cellService = new CellServiceImpl();
        cellService.depositMoney(income);
        Assertions.assertEquals(17000, cellService.getBalance());
    }

    @Test
    @DisplayName("Извлечение купюр из соответствующих ячеек и получение баланса")
    public void shouldRemoveAllBillsTest() {
        int amount = 6000;
        Map<BillValues, Integer> income = new HashMap<>();
        income.put(BillValues.ONE_THOUSAND, 7);
        income.put(BillValues.FIVE_THOUSANDS, 2);
        CellService cellService = new CellServiceImpl();
        cellService.depositMoney(income);
        cellService.withdrawMoney(amount);
        Assertions.assertEquals(11000, cellService.getBalance());
    }
}
