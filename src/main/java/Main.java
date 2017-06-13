import ResourcesManager.FileManager;
import Model.*;
import Solver.ParallelClarkWright;

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
        System.out.println("Capacità veicoli: "+instance.getVehiclesCapacity()+ "   Numero veicoli: "+instance.getNumberVehicles() +"   Numero clienti: "+instance.getNumberCustomers());
        WarehouseNode warehouseNode = instance.getWarehouseNode();
        System.out.println("Nodo Magazzino,  X: "+warehouseNode.getX() +"   Y: "+warehouseNode.getY());
        System.out.println("\nNodi Clienti:");
        for(DeliveryNode clientNode : instance.getNodesList()){
            if(clientNode.isDelivery()){
                System.out.println("X: "+clientNode.getX()+"  Y: "+clientNode.getY()+"  Delivery: "+clientNode.getDelivery());
            }else{
                System.out.println("X: "+clientNode.getX()+"  Y: "+clientNode.getY()+"  Pick-up : "+clientNode.getPickup());
            }
        }
        */


        for(Instance instance: fileManager.readInstances()){
            System.out.println("Name instance: "+instance.getNameInstance());
            System.out.println("Capacità veicoli: "+instance.getVehiclesCapacity()+ "   Numero veicoli: "+instance.getNumberVehicles() +"   Numero clienti: "+instance.getNumberCustomers());
            WarehouseNode warehouseNode = instance.getWarehouseNode();
            System.out.println("Nodo Magazzino,  X: "+warehouseNode.getX() +"   Y: "+warehouseNode.getY());
            System.out.println("\nNodi Clienti:");
            for(Node node : instance.getNodesList()) {
                if (node instanceof DeliveryNode) {
                    System.out.println("X: " + node.getX() + "  Y: " + node.getY() + "  Delivery: " + ((DeliveryNode) node).getDelivery());
                } else {
                    System.out.println("X: " + node.getX() + "  Y: " + node.getY() + "  Pick-up : " + ((PickupNode) node).getPickup());
                }
            }
            System.out.println("\n");
            SavingsMatrix saving = new SavingsMatrix(instance.getNodesList(),instance.getWarehouseNode(), new DistanceMatrix(instance.getNodesList(),instance.getWarehouseNode()));
            //System.out.println("SAVING "+saving.getSortedSaving());

            ParallelClarkWright pcw = new ParallelClarkWright(instance);


            break;
        }

    }
}