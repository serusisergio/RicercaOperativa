/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class WarehouseNode extends Node{
    private int capacityVehicles = 0;

    WarehouseNode(int x,int y,int capacityVehicles){
        super(x,y);
        this.capacityVehicles = capacityVehicles;
    }

    public int getCapacityVehicles() {
        return capacityVehicles;
    }

    private void setCapacityVehicles(int capacityVehicles) {
        this.capacityVehicles = capacityVehicles;
    }


}
