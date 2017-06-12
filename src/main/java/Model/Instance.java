package Model;
import java.util.ArrayList;



/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Instance {
    private int capacityVehicles = 0;
    private int numberCustomers  = 0;
    private int numberVehicles   = 0;
    private WarehouseNode warehouseNode;
    private String nameInstance  = "";
    private ArrayList<DeliveryNode> deliveryNodeHashMap;

    public Instance(int capacityVehicles, int numberCustomers, int numberVehicles, WarehouseNode warehouseNode, String nameInstance, ArrayList<DeliveryNode> deliveryNodeHashMap){
        setCapacityVehicles(capacityVehicles);
        setNumberCustomers(numberCustomers);
        setNumberVehicles(numberVehicles);
        setWarehouseNode(warehouseNode);
        setNameInstance(nameInstance);
        setDeliveryNodeHashMap(deliveryNodeHashMap);
    }

    public int getCapacityVehicles() {
        return capacityVehicles;
    }

    protected void setCapacityVehicles(int capacityVehicles) {
        this.capacityVehicles = capacityVehicles;
    }

    public int getNumberCustomers() {
        return numberCustomers;
    }

    protected void setNumberCustomers(int numberCustomers) {
        this.numberCustomers = numberCustomers;
    }

    public int getNumberVehicles() {
        return numberVehicles;
    }

    protected void setNumberVehicles(int numberVehicles) {
        this.numberVehicles = numberVehicles;
    }

    public WarehouseNode getWarehouseNode() {
        return warehouseNode;
    }

    protected void setWarehouseNode(WarehouseNode warehouseNode) {
        this.warehouseNode = warehouseNode;
    }

    public String getNameInstance() {
        return nameInstance;
    }

    protected void setNameInstance(String nameInstance) {
        this.nameInstance = nameInstance;
    }

    public ArrayList<DeliveryNode> getDeliveryNodeHashMap() {
        return deliveryNodeHashMap;
    }

    protected void setDeliveryNodeHashMap(ArrayList<DeliveryNode> deliveryNodeHashMap) {
        this.deliveryNodeHashMap = deliveryNodeHashMap;
    }
}
