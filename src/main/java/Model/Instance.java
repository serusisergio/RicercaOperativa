package Model;
import java.util.ArrayList;



/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Instance {
    private int vehiclesCapacity = 0;
    private int numberCustomers  = 0;
    private int numberVehicles   = 0;
    private WarehouseNode warehouseNode;
    private String nameInstance  = "";
    private ArrayList<Node> nodesList;

    public Instance(int vehiclesCapacity, int numberCustomers, int numberVehicles, WarehouseNode warehouseNode, String nameInstance, ArrayList<Node> nodesList){
        setVehiclesCapacity(vehiclesCapacity);
        setNumberCustomers(numberCustomers);
        setNumberVehicles(numberVehicles);
        setWarehouseNode(warehouseNode);
        setNameInstance(nameInstance);
        setNodesList(nodesList);
    }

    public int getVehiclesCapacity() {
        return vehiclesCapacity;
    }

    protected void setVehiclesCapacity(int vehiclesCapacity) {
        this.vehiclesCapacity = vehiclesCapacity;
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

    public ArrayList<Node> getNodesList() {
        return nodesList;
    }

    protected void setNodesList(ArrayList<Node> nodesList) {
        this.nodesList = nodesList;
    }
}
