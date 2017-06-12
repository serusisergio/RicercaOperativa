package Model;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class DeliveryNode extends Node{
    private int delivery = 0;

    public DeliveryNode(int x, int y, int delivery){
        super(x,y);
        setDelivery(delivery);
    }

    public int getDelivery() {
        return delivery;
    }

    protected void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryNode that = (DeliveryNode) o;

        return delivery == that.delivery && super.getX() == that.getX() && super.getY() == that.getY();
    }

    @Override
    public int hashCode() {
        int result = 0;
        result += 17 * delivery;
        result += 17 * super.getX();
        result += 17 * super.getY();

        return result;
    }
}
