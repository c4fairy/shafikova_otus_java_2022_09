import dicts.BillValues;
import impls.CellImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.Validation;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTest {
    Map<BillValues, Integer> outcome = new HashMap<>();
    Map<BillValues, CellImpl> currentCells = new HashMap<>();

    @Test
    @DisplayName("Можем выдать запрошенную сумму")
    public void shouldAllowAmountWithdrawTest() {
        currentCells.put(BillValues.ONE_HUNDRED, new CellImpl(2));
        currentCells.put(BillValues.FIVE_HUNDREDS, new CellImpl(20));
        outcome = new HashMap<>();
        outcome.put(BillValues.FIVE_HUNDREDS, 2);
        boolean result = Validation.isAmountAllowed(outcome, currentCells);
        assertTrue(result);
    }

    @Test
    @DisplayName("Не можем выдать запрошенную сумму")
    public void shouldNotAllowAmountWithdrawTest() {
        currentCells.put(BillValues.TWO_THOUSANDS, new CellImpl(1));
        outcome.put(BillValues.TWO_THOUSANDS, 2);
        boolean result = Validation.isAmountAllowed(outcome, currentCells);
        assertFalse(result);
    }
}
