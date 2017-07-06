package core.model;

import java.util.ArrayList;


/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Instance {
    private int vehiclesCapacity = 0;
    private int numberCustomers = 0;
    private int numberVehicles = 0;
    private WarehouseNode warehouseNode;
    private String instanceName = "";
    private ArrayList<Node> nodesList;
    private double bestSolution = 0;

    public Instance(int vehiclesCapacity, int numberCustomers, int numberVehicles, WarehouseNode warehouseNode, String instanceName, ArrayList<Node> nodesList, double bestSolution) {
        setVehiclesCapacity(vehiclesCapacity);
        setNumberCustomers(numberCustomers);
        setNumberVehicles(numberVehicles);
        setWarehouseNode(warehouseNode);
        setInstanceName(instanceName);
        setNodesList(nodesList);
        setBestSolution(bestSolution);
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

    public String getInstanceName() {
        return instanceName;
    }

    protected void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public ArrayList<Node> getNodesList() {
        return nodesList;
    }

    protected void setNodesList(ArrayList<Node> nodesList) {
        this.nodesList = nodesList;
    }

    public double getBestSolution() {
        return bestSolution;
    }

    public void setBestSolution(double bestSolution) {
        this.bestSolution = bestSolution;
    }
}
