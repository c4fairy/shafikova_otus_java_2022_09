import impls.CellImpl;
import interfaces.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CellImplTest {
    @Test
    @DisplayName("Добавление купюры в ячейку")
    public void shouldAddBillTest() {
        Cell cell = new CellImpl();
        cell.addBill(2);
        Assertions.assertEquals(2, cell.getCount());
    }

    @Test
    @DisplayName("Извлечение купюры из ячейки")
    public void shouldRemoveBillTest() {
        Cell cell = new CellImpl();
        cell.addBill(3);
        cell.removeBill(2);
        Assertions.assertEquals(1, cell.getCount());
    }

    @Test
    @DisplayName("Получение количества купюр в ячейке")
    public void shouldCountBillTest() {
        Cell cell = new CellImpl();
        cell.addBill(8);
        Assertions.assertEquals(8, cell.getBalance());
    }
}
