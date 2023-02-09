package impls;

import interfaces.Cell;

//ячейка
public class CellImpl implements Cell {
    private int count;

    public CellImpl(int count) {
        this.count = count;
    }

    public CellImpl() {
    }

    public void addBill(int count) {
        this.count += count;
    }

    public void removeBill(int count) {
        this.count -= count;
    }

    public int getBalance() {
        return this.count;
    }

    public int getCount() {return this.count; }
}
