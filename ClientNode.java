/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class ClientNode extends Node{
    private int delivery = 0;
    private int pick_up  = 0;

    ClientNode(int x,int y,int delivery, int pick_up){
        super(x,y);
        setDelivery(delivery);
        setPick_up(pick_up);
    }

    public int getDelivery() {
        return delivery;
    }

    private void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public int getPick_up() {
        return pick_up;
    }

    private void setPick_up(int pick_up) {
        this.pick_up = pick_up;
    }

    public boolean isDelivery(){
        return (getDelivery()>0);
    }
}
