package Model;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class WarehouseNode extends Node {
    public WarehouseNode(int x, int y, int id) {
        super(x, y, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarehouseNode that = (WarehouseNode) o;

        return super.getX() == that.getX() && super.getY() == that.getY();
    }

    @Override
    public int hashCode() {
        int result = 0;
        result += 17 * super.getX();
        result += 17 * super.getY();

        return result;
    }
    @Override
    public String toString() {
        return "Warehouse " + id;
    }

}
