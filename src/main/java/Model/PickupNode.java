package Model;

/**
 * Created by stefano on 12/06/17.
 */
public class PickupNode extends Node{
    private int pickup = 0;

    public PickupNode(int x, int y, int pickup){
        super(x,y);
        setPickup(pickup);
    }


    public int getPickup() {
        return pickup;
    }

    protected void setPickup(int pickup) {
        this.pickup = pickup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PickupNode that = (PickupNode) o;

        return pickup == that.pickup && super.getX() == that.getX() && super.getY() == that.getY();
    }

    @Override
    public int hashCode() {
        int result = 0;
        result += 17 * pickup;
        result += 17 * super.getX();
        result += 17 * super.getY();

        return result;
    }
}
