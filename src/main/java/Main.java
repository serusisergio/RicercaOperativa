import core.cw.ParallelClarkWright;
import core.heuristics.BestExchange;
import core.heuristics.BestRelocate;
import core.model.*;
import resourcesManager.FileManager;
import core.cw.ClarkWright;
import core.cw.SequentialClarkWright;

import java.util.List;
import java.util.Random;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("Implementazione dell’ algoritmo euristico di Clarke & Wright nelle sue versioni parallela e sequenziale\n");
        FileManager fileManager = new FileManager();


        Instance instance = fileManager.readInstance("A1.txt");

        ClarkWright pcw = new ParallelClarkWright(instance);
        if(checkValidity(pcw)){

        }else {
            System.out.println(pcw.getFinalRoutes());
            double initGain = pcw.getTotalCost();
            System.out.println(initGain);

            BestExchange.doBestExchangesNew(pcw);
            if(!checkValidity(pcw)){
                double endGain = pcw.getTotalCost();
                System.out.println(endGain);

                System.out.println(pcw.getFinalRoutes());

                System.out.println("Guadagno:" + (initGain - endGain));
            }else{
                System.out.println("DIO PORCOO");
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

    public static boolean checkValidity(ClarkWright cw){
        boolean flag= false;
        List<Route> routeList = cw.getFinalRoutes();
        int i=0;
        for(Route route: routeList){
            int valueDelivery=0;
            int valuePick=0;
            for(Node node: route.getRoute()){
                if(node instanceof DeliveryNode){
                    valueDelivery += ((DeliveryNode) node).getDelivery();
                }else{
                    if(node instanceof PickupNode){
                        valuePick += ((PickupNode) node).getPickup();
                    }
                }
            }
            if(valueDelivery>cw.getInstance().getVehiclesCapacity()){
                System.out.println("La route supera Delivery, Route:"+i);
                flag = true;
            }
            i++;
            /*
            if(valuePick>valueDelivery){
                System.out.println("La route scarica piu di quanto carica");
                flag = true;
            }*/
        }
        return flag;
    }
}