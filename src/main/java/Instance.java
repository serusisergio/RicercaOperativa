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
    private ArrayList<ClientNode> clientNodeHashMap;

    Instance(int capacityVehicles,int numberCustomers,int numberVehicles,WarehouseNode warehouseNode,String nameInstance,ArrayList<ClientNode> clientNodeHashMap){
        setCapacityVehicles(capacityVehicles);
        setNumberCustomers(numberCustomers);
        setNumberVehicles(numberVehicles);
        setWarehouseNode(warehouseNode);
        setNameInstance(nameInstance);
        setClientNodeHashMap(clientNodeHashMap);
    }

    public int getCapacityVehicles() {
        return capacityVehicles;
    }

    private void setCapacityVehicles(int capacityVehicles) {
        this.capacityVehicles = capacityVehicles;
    }

    public int getNumberCustomers() {
        return numberCustomers;
    }

    private void setNumberCustomers(int numberCustomers) {
        this.numberCustomers = numberCustomers;
    }

    public int getNumberVehicles() {
        return numberVehicles;
    }

    private void setNumberVehicles(int numberVehicles) {
        this.numberVehicles = numberVehicles;
    }

    public WarehouseNode getWarehouseNode() {
        return warehouseNode;
    }

    private void setWarehouseNode(WarehouseNode warehouseNode) {
        this.warehouseNode = warehouseNode;
    }

    public String getNameInstance() {
        return nameInstance;
    }

    private void setNameInstance(String nameInstance) {
        this.nameInstance = nameInstance;
    }

    public ArrayList<ClientNode> getClientNodeHashMap() {
        return clientNodeHashMap;
    }

    private void setClientNodeHashMap(ArrayList<ClientNode> clientNodeHashMap) {
        this.clientNodeHashMap = clientNodeHashMap;
    }
}
