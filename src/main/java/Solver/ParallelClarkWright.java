package Solver;

import Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by stefano on 12/06/17.
 */
public class ParallelClarkWright {

    private Instance instance;
    private List<Route> bhRoutes, lhRoutes, finalRoutes;
    private DistanceMatrix distances;
    private SavingsMatrix savings;

    public ParallelClarkWright(Instance i) {
        this.instance = i;

        distances = new DistanceMatrix(instance.getNodesList(), instance.getWarehouseNode());
        savings = new SavingsMatrix(instance.getNodesList(), instance.getWarehouseNode(), distances);

        bhRoutes = new ArrayList<>();
        lhRoutes = new ArrayList<>();
        finalRoutes = new ArrayList<>();

        solveBH();
        solveLH();

        mergeRoutes();
    }

    private void solveLH() {
        //ottengo solo i nodi di delivery
        List<Node> lhNodes = instance.getNodesList().stream().filter(node -> node instanceof DeliveryNode).collect(Collectors.toList());

        //creo le rotte iniziali
        for(Node n : lhNodes){
            Route r = new Route(instance.getWarehouseNode(), n, distances, instance.getVehiclesCapacity());
            lhRoutes.add(r);
        }

        savings.getSortedSaving().forEach((pair, save) -> {
            if(lhRoutes.size() > bhRoutes.size() && lhRoutes.size() > instance.getNumberVehicles()){

                Node iNode = pair.getKey();
                Node jNode = pair.getValue();

                //cerca una rotta il cui secondo elemento sia j
                Route first = null;
                for(Route r : lhRoutes){
                    if(r.getRoute().get(1).equals(jNode))
                        first = r;
                }

                if(first != null){
                    //cerco una rotta (diversa) il cui penultimo elemento sia i
                    Route second = null;
                    int secondIdx = -1;

                    int i=0;
                    for(Route r : lhRoutes){
                        if(!r.equals(first) && r.getRoute().get(r.getRoute().size()-2).equals(iNode)){
                            second = r;
                            secondIdx = i;
                            break;
                        }
                        i++;
                    }

                    //se ho trovato entrambe le rotte, uniscile
                    if(second != null){
                        try {
                            first.mergeEnd(second);
                            lhRoutes.remove(secondIdx);
                        } catch (LoadExceededException e){}
                    }
                }
            }
        });

        System.out.println("LINEHAUL ROUTES");
        System.out.println(lhRoutes);
    }


    private void solveBH() {
        //ottengo solo i nodi di pickup
        List<Node> bhNodes = instance.getNodesList().stream().filter(node -> node instanceof PickupNode).collect(Collectors.toList());

        //creo le rotte iniziali
        for(Node n : bhNodes){
            Route r = new Route(instance.getWarehouseNode(), n, distances, instance.getVehiclesCapacity());
            bhRoutes.add(r);
        }

        savings.getSortedSaving().forEach((pair, save) -> {
            Node iNode = pair.getKey();
            Node jNode = pair.getValue();

            //cerca una rotta il cui secondo elemento sia j
            Route first = null;
            for(Route r : bhRoutes){
                if(r.getRoute().get(1).equals(jNode))
                    first = r;
            }

            if(first != null){
               //cerco una rotta (diversa) il cui penultimo elemento sia i
                Route second = null;
                int secondIdx = -1;

                int i=0;
                for(Route r : bhRoutes){
                    if(!r.equals(first) && r.getRoute().get(r.getRoute().size()-2).equals(iNode)){
                        second = r;
                        secondIdx = i;
                        break;
                    }
                    i++;
                }

                //se ho trovato entrambe le rotte, uniscile
                if(second != null){
                    try {
                        first.mergeEnd(second);
                        bhRoutes.remove(secondIdx);
                    } catch (LoadExceededException e){}
                }
            }
        });

        System.out.println("BACKHAUL ROUTES");
        System.out.println(bhRoutes);
    }

    private void mergeRoutes() {

    }
}
