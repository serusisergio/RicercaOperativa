package Solver;

import Model.*;
import javafx.util.Pair;

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

        mergeLhBh();
    }

    private void solveLH() {
        //ottengo solo i nodi di delivery
        List<Node> lhNodes = instance.getNodesList().stream().filter(node -> node instanceof DeliveryNode).collect(Collectors.toList());

        //creo le rotte iniziali
        for (Node n : lhNodes) {
            Route r = new Route(instance.getWarehouseNode(), n, distances, instance.getVehiclesCapacity());
            lhRoutes.add(r);
        }

        savings.getSortedSaving().forEach((pair, save) -> {
            if (lhRoutes.size() > bhRoutes.size() && lhRoutes.size() > instance.getNumberVehicles()) {

                if (lhRoutes.size() < instance.getNumberVehicles()) {
                    System.out.println("DIO");
                }

                mergeRoute(pair, lhRoutes);
            }
        });
    }

    private void solveBH() {
        //ottengo solo i nodi di pickup
        List<Node> bhNodes = instance.getNodesList().stream().filter(node -> node instanceof PickupNode).collect(Collectors.toList());

        //creo le rotte iniziali
        for (Node n : bhNodes) {
            Route r = new Route(instance.getWarehouseNode(), n, distances, instance.getVehiclesCapacity());
            bhRoutes.add(r);
        }

        savings.getSortedSaving().forEach((pair, save) -> mergeRoute(pair, bhRoutes));
    }

    private void mergeLhBh() {

        finalRoutes.addAll(lhRoutes);
        finalRoutes.addAll(bhRoutes);

        savings.getSortedSaving().forEach((pair, save) -> {
            Node iNode = pair.getKey();
            Node jNode = pair.getValue();

            if (iNode instanceof PickupNode && jNode instanceof DeliveryNode) {
                mergeMixedRoutes(pair, finalRoutes);
            }
        });

        System.out.println("FINAL ROUTES");
        System.out.println(finalRoutes);
    }

    private void mergeRoute(Pair<Node, Node> pair, List<Route> routes) {
        Node iNode = pair.getKey();
        Node jNode = pair.getValue();

        //cerca una rotta il cui secondo elemento sia j
        Route first = null;
        for (Route r : routes) {
            if (r.getRoute().get(1).equals(jNode))
                first = r;
        }

        if (first != null) {
            //cerco una rotta (diversa) il cui penultimo elemento sia i
            Route second = null;
            int secondIdx = -1;

            int i = 0;
            for (Route r : routes) {
                if (!r.equals(first) && r.getRoute().get(r.getRoute().size() - 2).equals(iNode)) {
                    second = r;
                    secondIdx = i;
                    break;
                }
                i++;
            }

            //se ho trovato entrambe le rotte, uniscile
            if (second != null) {

                try {
                    first.mergeEnd(second);
                    routes.remove(secondIdx);
                } catch (LoadExceededException e) {
                }
            }
        }
    }

    private void mergeMixedRoutes(Pair<Node, Node> pair, List<Route> routes) {
        Node iNode = pair.getKey();
        Node jNode = pair.getValue();

        //cerca una rotta il cui secondo elemento sia j
        Route first = null;
        for (Route r : routes) {
            if (r.getRoute().get(r.getRoute().size() - 2).equals(jNode))
                first = r;
        }

        if (first != null) {
            //cerco una rotta (diversa) il cui penultimo elemento sia i
            Route second = null;
            int secondIdx = -1;

            int i = 0;
            for (Route r : routes) {
                if (!r.equals(first) && r.getRoute().get(1).equals(iNode)) {
                    second = r;
                    secondIdx = i;
                    break;
                }
                i++;
            }

            //se ho trovato entrambe le rotte, uniscile
            if (second != null) {
                try {
                    first.mergeEnd(second);
                    routes.remove(secondIdx);
                } catch (LoadExceededException e) {
                }
            }
        }
    }

    public double getTotalCost() {
        return finalRoutes.stream().map(r -> r.getTotalDistance()).reduce(Double::sum).get();
    }

    public List<Route> getFinalRoutes() {
        return finalRoutes;
    }

    public String getSolutionDetail() {
        StringBuilder sb = new StringBuilder();

        sb.append("Text File with Solution Of Problem: ").append(instance.getNameInstance());

        sb.append("\n\nPROBLEM DETAILS");
        sb.append("\nCustomers = ").append(instance.getNumberCustomers());
        sb.append("\nMx Load = ").append(instance.getVehiclesCapacity());
        sb.append("\nMax Cost = 99999999999999");

        sb.append("\n\nSOLUTION DETAILS:");
        sb.append("\nTotal Cost = ").append(this.getTotalCost());
        sb.append("\nRoutes Of the Solution = ").append(this.finalRoutes.size());

        int i = 0;
        for (Route r : finalRoutes) {
            sb.append("\n");
            sb.append("\nROUTE ").append(i);
            sb.append("\nCost = ").append(r.getTotalDistance());
            sb.append("\nDelivery Load = ").append(r.getLhLoad());
            sb.append("\nPick-Up Load = ").append(r.getBhLoad());
            sb.append("\nCustomers in Route = ").append(r.getRoute().size() - 2);
            sb.append("\nVertex Sequence :\n");

            for (Node n : r.getRoute()) {
                sb.append(n.getId()).append(" ");
            }
            i++;
        }

        return sb.toString();
    }
}

/*
Text File with Solution Of Problem: A4.txt

PROBLEM DETAILS:
Customers = 25
Max Load = 4050
Max Cost = 99999999999999

SOLUTION DETAILS:
Total Cost = 155796.0
Routes Of the Solution = 3

ROUTE 0:
Cost = 358171.0000
Delivery Load = 2684
Pick-Up  Load = 549
Customers in Route = 6
Vertex Sequence :
0 17 9 24 20 22 1 0

ROUTE 1:
Cost = 515010.0000
Delivery Load = 3431
Pick-Up  Load = 1603
Customers in Route = 10
Vertex Sequence :
0 11 15 8 13 25 12 10 3 2 5 0

ROUTE 2:
Cost = 684782.0000
Delivery Load = 3934
Pick-Up  Load = 388
Customers in Route = 9
Vertex Sequence :
0 18 23 19 6 7 16 21 14 4 0

 */
