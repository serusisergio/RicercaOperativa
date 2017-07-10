package core.cw;

import core.model.*;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/** Superclasse astratta delle euristiche Clark&Wright.
 * Contiene i metodi comuni ad entrame le versioni (unione delle rotte, controlli,
 * stampa a video e su file).
 * Le sottoclassi devono implementare i metodi astratti solveLH (creazione delle rotte di linehaul)
 * e solveBH (crezione delle rotte di backhaul)
 */
public abstract  class ClarkeWright {
    private Instance instance;
    protected List<Route> bhRoutes, lhRoutes, finalRoutes;
    protected DistanceMatrix distances;
    protected SavingsMatrix savings;
    private double time;

    public ClarkeWright(Instance i) {
        this.setInstance(i);

        distances = new DistanceMatrix(getInstance().getNodesList(), getInstance().getWarehouseNode());
        savings = new SavingsMatrix(getInstance().getNodesList(), getInstance().getWarehouseNode(), distances);

        bhRoutes = new ArrayList<>();
        lhRoutes = new ArrayList<>();
        finalRoutes = new ArrayList<>();

        //algoritmo vero e proprio
        //prima vengono create le rotte di backhaul, poi quelle di linehaul
        solveBH();
        solveLH();

        //infine i due tipi di rotte sono uniti, dove possibile
        mergeLhBh();
    }

    protected abstract void solveBH();
    protected abstract void solveLH();


    /**
     * Unisce rotte di linehaul con rotte di backhaul,
     * utilizzando i savings per fare prima i merge piu' covenienti
     */
    private void mergeLhBh() {

        finalRoutes.addAll(lhRoutes);
        finalRoutes.addAll(bhRoutes);

        savings.getSortedSavings().forEach(pair -> {
            Node iNode = pair.getKey();
            Node jNode = pair.getValue();

            if (iNode instanceof PickupNode && jNode instanceof DeliveryNode) {
                mergeLeft(pair, finalRoutes);
            }
        });

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

    public SavingsMatrix getSavings(){
        return savings;
    }

    public List<Route> getFinalRoutes() {
        return finalRoutes;
    }

    public String getSolutionDetail() {
        StringBuilder sb = new StringBuilder();

        sb.append("Text File with Solution Of Problem: ").append(getInstance().getInstanceName());

        sb.append("\n\nPROBLEM DETAILS");
        sb.append("\nCustomers = ").append(getInstance().getNumberCustomers());
        sb.append("\nMx Load = ").append(getInstance().getVehiclesCapacity());
        sb.append("\nMax Cost = 99999999999999");

        sb.append("\n\nSOLUTION DETAILS:");
        sb.append("\nTotal Cost = ").append(this.getTotalCost());
        sb.append("\nRoutes Of the Solution = ").append(this.finalRoutes.size());

        int i = 0;
        for (Route r : finalRoutes) {
            sb.append("\n");
            sb.append("\nROUTE ").append(i);
            sb.append("\nCost = ").append(r.getTotalDistance());
            sb.append("\nDelivery Load = ").append(r.getLHLoad());
            sb.append("\nPick-Up Load = ").append(r.getBHLoad());
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

    public void setFinalRoutes(List<Route> finalRoutes){
        this.finalRoutes = finalRoutes;
    }

    public Instance getInstance() {
        return instance;
    }

    public void setInstance(Instance instance) {
        this.instance = instance;
    }

    public boolean checkValidity(){
        boolean flag= true;

        int i=0;
        for(Route route: finalRoutes){
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
            if(valueDelivery>instance.getVehiclesCapacity()){
                System.out.println("La parte LH supera il carico, Route:"+i);
                flag = false;
            }
            if(valuePick>instance.getVehiclesCapacity()){
                System.out.println("La parte BH route supera il carico, Route:"+i);
                flag = false;
            }
        }
        return flag;
    }

    public double getSolutionMargin(){
        return  getTotalCost() / instance.getBestSolution() * 100 - 100;
    }

    public void saveToFile(String fileName){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)))) {
            bw.write(getSolutionDetail());
            bw.newLine();
            bw.newLine();
            bw.write("Total execution time = " + time);
            bw.newLine();
            bw.newLine();
            bw.write("Margin from the best known solution = " + getSolutionMargin());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
