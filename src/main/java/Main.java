import ResourcesManager.FileManager;
import Model.*;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Implementazione dell’ algoritmo euristico di Clarke & Wright nelle sue versioni parallela e sequenziale\n");
        FileManager fileManager = new FileManager();

        /*
        Instance instance = fileManager.readInstance("A1.txt");

        System.out.println("Name instance: "+instance.getNameInstance());
        System.out.println("Capacità veicoli: "+instance.getCapacityVehicles()+ "   Numero veicoli: "+instance.getNumberVehicles() +"   Numero clienti: "+instance.getNumberCustomers());
        WarehouseNode warehouseNode = instance.getWarehouseNode();
        System.out.println("Nodo Magazzino,  X: "+warehouseNode.getX() +"   Y: "+warehouseNode.getY());
        System.out.println("\nNodi Clienti:");
        for(ClientNode clientNode : instance.getClientNodeHashMap()){
            if(clientNode.isDelivery()){
                System.out.println("X: "+clientNode.getX()+"  Y: "+clientNode.getY()+"  Delivery: "+clientNode.getDelivery());
            }else{
                System.out.println("X: "+clientNode.getX()+"  Y: "+clientNode.getY()+"  Pick-up : "+clientNode.getPick_up());
            }
        }
        */
        for(Instance instance: fileManager.readInstances()){
            System.out.println("Name instance: "+instance.getNameInstance());
            System.out.println("Capacità veicoli: "+instance.getCapacityVehicles()+ "   Numero veicoli: "+instance.getNumberVehicles() +"   Numero clienti: "+instance.getNumberCustomers());
            WarehouseNode warehouseNode = instance.getWarehouseNode();
            System.out.println("Nodo Magazzino,  X: "+warehouseNode.getX() +"   Y: "+warehouseNode.getY());
            System.out.println("\nNodi Clienti:");
            for(ClientNode clientNode : instance.getClientNodeHashMap()) {
                if (clientNode.isDelivery()) {
                    System.out.println("X: " + clientNode.getX() + "  Y: " + clientNode.getY() + "  Delivery: " + clientNode.getDelivery());
                } else {
                    System.out.println("X: " + clientNode.getX() + "  Y: " + clientNode.getY() + "  Pick-up : " + clientNode.getPick_up());
                }
            }
            System.out.println("\n");
        }

    }
}