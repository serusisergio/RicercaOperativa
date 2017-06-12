package Model;

/**
 * Created by stefano on 12/06/17.
 */
public class PickupNode extends Node{
    private int pickup = 0;

    public PickupNode(int x, int y, int delivery, int pickup){
        super(x,y);
        setPickup(pickup);
    }


    public int getPickup() {
        return pickup;
    }

    protected void setPickup(int pickup) {
        this.pickup = pickup;
    }
}
