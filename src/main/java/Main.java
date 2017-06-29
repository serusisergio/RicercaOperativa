import core.cw.ParallelClarkWright;
import core.heuristics.BestExchange;
import core.heuristics.BestRelocate;
import resourcesManager.FileManager;
import core.cw.ClarkWright;
import core.cw.SequentialClarkWright;
import core.model.Instance;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("Implementazione dell’ algoritmo euristico di Clarke & Wright nelle sue versioni parallela e sequenziale\n");
        FileManager fileManager = new FileManager();


        Instance instance = fileManager.readInstance("A2.txt");

        ClarkWright pcw = new ParallelClarkWright(instance);
        //System.out.println(pcw.getSolutionDetail());
        BestExchange.doBestExchanges(pcw);
        System.out.println(pcw.getSolutionDetail());
        BestRelocate.doBestRelocates(pcw);
        System.out.println(pcw.getSolutionDetail());


/*
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
            System.out.println("Total Cost = " + pcw.getTotalCost());


            break;
        }
*/
    }
}