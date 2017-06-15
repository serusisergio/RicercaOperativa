package Solver;

import Model.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefano on 15/06/17.
 */
public abstract  class ClarkWright {
    protected Instance instance;
    protected List<Route> bhRoutes, lhRoutes, finalRoutes;
    protected DistanceMatrix distances;
    protected SavingsMatrix savings;

    public ClarkWright(Instance i) {
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

    protected abstract void solveBH();
    protected abstract void solveLH();


    private void mergeLhBh() {

        finalRoutes.addAll(lhRoutes);
        finalRoutes.addAll(bhRoutes);

        savings.getSortedSaving().forEach(pair -> {
            Node iNode = pair.getKey();
            Node jNode = pair.getValue();

            if (iNode instanceof PickupNode && jNode instanceof DeliveryNode) {
                mergeLeft(pair, finalRoutes);
            }
        });

        System.out.println("FINAL ROUTES");
        System.out.println(finalRoutes);
    }

    protected void mergeLeft(Pair<Node, Node> pair, List<Route> routes) {
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

    protected void mergeRight(Pair<Node, Node> pair, List<Route> routes) {
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

}
