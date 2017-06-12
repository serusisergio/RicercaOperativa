package Model;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class DeliveryNode extends Node{
    private int delivery = 0;

    public DeliveryNode(int x, int y, int delivery, int pick_up){
        super(x,y);
        setDelivery(delivery);
    }

    public int getDelivery() {
        return delivery;
    }

    protected void setDelivery(int delivery) {
        this.delivery = delivery;
    }
}
