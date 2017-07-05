import core.cw.ParallelClarkWright;
import core.heuristics.BestExchange;
import core.model.*;
import resourcesManager.FileManager;
import core.cw.ClarkWright;

import java.util.List;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("Implementazione dell’ algoritmo euristico di Clarke & Wright nelle sue versioni parallela e sequenziale\n");
        FileManager fileManager = new FileManager();


        Instance instance = fileManager.readInstance("N4.txt");

        ClarkWright pcw = new ParallelClarkWright(instance);




        if(!pcw.checkValidity()){
            System.out.println("Errore sovraccarico rotte CW");

        }else {
            System.out.println(pcw.getFinalRoutes());
            double initGain = pcw.getTotalCost();
            System.out.println(initGain);

            BestExchange.doBestExchanges(pcw);
            if(pcw.checkValidity()){
                double endGain = pcw.getTotalCost();
                System.out.println(endGain);

                System.out.println(pcw.getFinalRoutes());

                System.out.println("Guadagno:" + (initGain - endGain));
            }else{
                System.out.println(pcw.getFinalRoutes());
            }

        }




        //BestRelocate.doBestRelocates(pcw);
        //System.out.println(pcw.getTotalCost());

        //System.out.println(pcw.getFinalRoutes());



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