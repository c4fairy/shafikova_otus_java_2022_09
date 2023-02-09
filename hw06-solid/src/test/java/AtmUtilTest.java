import dicts.BillValues;
import impls.CellImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.AtmUtil;

import java.util.HashMap;
import java.util.Map;

public class AtmUtilTest {

    public Map<BillValues, CellImpl> currentCells = new HashMap<>() {{
        put(BillValues.ONE_HUNDRED, new CellImpl(5));
        put(BillValues.TWO_HUNDREDS, new CellImpl(0));
        put(BillValues.FIVE_HUNDREDS, new CellImpl(30));
        put(BillValues.ONE_THOUSAND, new CellImpl(5));
        put(BillValues.TWO_THOUSANDS, new CellImpl(0));
        put(BillValues.FIVE_THOUSANDS, new CellImpl(1));
    }};

    @Test
    @DisplayName("Рассчитано минимальное количество банкнот")
    public void shouldGiveMinBillsTest() {
        int sum = 0;
        Map<BillValues, Integer> cellOutcome = AtmUtil.getMinBills(currentCells,5300);
        for (Map.Entry<BillValues, Integer> outcome : cellOutcome.entrySet()) {
            sum += outcome.getValue();
        }
        Assertions.assertEquals(4, sum);
    }
}
