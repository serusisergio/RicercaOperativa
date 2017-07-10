package core.model;


import core.cw.DistanceMatrix;
import core.cw.LoadExceededException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Route {

    private double totalDistance = 0;

    private int vehicleCapacity;
    private int bhLoad = 0;
    private int lhLoad = 0;
    private List<Node> route = new ArrayList<>();
    private DistanceMatrix distances;

    /**
     * Creazione iniziale della rotta. La rotta va dal warehouse al nodo nel parametro e torna al warehouse
     *
     * @param warehouse
     * @param node
     * @param distances
     * @param vehicleCapacity
     */
    public Route(Node warehouse, Node node, DistanceMatrix distances, int vehicleCapacity) {
        this.distances = distances;
        this.vehicleCapacity = vehicleCapacity;
        double distance = distances.getDistance(warehouse, node);

        route.add(warehouse);
        route.add(node);
        route.add(warehouse);

        totalDistance = distance * 2;

        if (node instanceof DeliveryNode) {
            lhLoad += ((DeliveryNode) node).getDelivery();
        } else {
            bhLoad += ((PickupNode) node).getPickup();
        }
    }

    /**
     * Unisce due rotte, attaccando quella in parametro alla fine di quella corrente
     *
     * @param r
     */
    public void mergeEnd(Route r) {

        //check if the merge is allowed
        if (this.bhLoad + r.bhLoad > vehicleCapacity)
            throw new LoadExceededException();

        if (this.lhLoad + r.lhLoad > vehicleCapacity)
            throw new LoadExceededException();


        //togli i warehouse dalle rotte
        Node last = this.route.remove(this.route.size() - 1);
        if (!(last instanceof WarehouseNode))
            throw new RuntimeException("Rotta che non finisce in warehouse!");

        Node first = r.route.remove(0);
        if (!(first instanceof WarehouseNode))
            throw new RuntimeException("Rotta che non inizia in warehouse!");

        Node newLast = this.route.get(this.route.size() - 1);
        Node newFirst = r.route.get(0);

        double newEdgeDistance = distances.getDistance(newFirst, newLast);

        //aggiorno la rotta
        this.route.addAll(r.route);


        //aggiorno la distanza
        this.totalDistance -= distances.getDistance(newLast, last);
        this.totalDistance -= distances.getDistance(newFirst, first);
        this.totalDistance += newEdgeDistance;
        this.totalDistance += r.totalDistance;

        if (totalDistance < 0) {
            //System.out.println("DIO");
        }

        //aggiorno i carichi
        this.bhLoad += r.bhLoad;
        this.lhLoad += r.lhLoad;
    }


    public List<Node> getRoute() {
        return route;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route route1 = (Route) o;

        return route.equals(route1.route);
    }

    @Override
    public int hashCode() {
        return route.hashCode();
    }

    @Override
    public String toString() {
        return route.toString();
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public int getBHLoad() {
        return bhLoad;
    }

    public int getLHLoad() {
        return lhLoad;
    }

    /**
     * Calcola il delta del costo della rotta, ottenuto togliendo il primo nodo e
     * inserendo al suo posto il secondo. Se lo scambio non e' ammissibile per
     * il superamento del carico, il delta e' MAX_INT (mai conveniente)
     *
     * @param exitingNode
     * @param enteringNode
     * @return
     */
    public double getExchangeDelta(Node exitingNode, Node enteringNode) {

        int exitingPosition = this.route.indexOf(exitingNode);

        Node pred = this.route.get(exitingPosition - 1);
        Node succ = this.route.get(exitingPosition + 1);

        if (!succ.equals(enteringNode)) {
            //controlla i carichi
            //se lo scambio fa superare il carico, restituisci MAX_INT
            //in modo che non sia mai fatto
            if (exitingNode instanceof DeliveryNode) {
                DeliveryNode delExitNode = (DeliveryNode) exitingNode;
                DeliveryNode delEnterNode = (DeliveryNode) enteringNode;

                if (lhLoad - delExitNode.getDelivery() + delEnterNode.getDelivery() > vehicleCapacity) {
                    return Integer.MAX_VALUE;
                }
            } else {
                PickupNode pickExitNode = (PickupNode) exitingNode;
                PickupNode pickEnterNode = (PickupNode) enteringNode;

                if (bhLoad - pickExitNode.getPickup() + pickEnterNode.getPickup() > vehicleCapacity)
                    return Integer.MAX_VALUE;
            }

            return -distances.getDistance(pred, exitingNode) - distances.getDistance(exitingNode, succ)
                    + distances.getDistance(pred, enteringNode) + distances.getDistance(enteringNode, succ);
        } else {
            Node otherSucc = this.route.get(exitingPosition + 2);
            double val = -distances.getDistance(pred, exitingNode) - distances.getDistance(exitingNode, enteringNode) - distances.getDistance(enteringNode, otherSucc)
                    + distances.getDistance(pred, enteringNode) + distances.getDistance(enteringNode, exitingNode) + distances.getDistance(exitingNode, otherSucc);

            return val;
        }

    }


    /**
     * Fai lo scambio tra due nodi, togliendo il nodo uscente e inserendo il nodo entrante.
     * Non vale per nodi contigui.
     * @param exitingNode
     * @param enteringNode
     */
    public void exchangeNodes(Node exitingNode, Node enteringNode) {
        double delta = getExchangeDelta(exitingNode, enteringNode);

        if (delta == Integer.MAX_VALUE)
            throw new RuntimeException("Tentativo non valido di scambiare " + exitingNode + " con " + enteringNode + " in rotta " + this);

        this.totalDistance += delta;

        //aggiorno i carichi
        if (exitingNode instanceof DeliveryNode) {
            lhLoad -= ((DeliveryNode) exitingNode).getDelivery();
            lhLoad += ((DeliveryNode) enteringNode).getDelivery();
        } else if (exitingNode instanceof PickupNode) {
            bhLoad -= ((PickupNode) exitingNode).getPickup();
            bhLoad += ((PickupNode) enteringNode).getPickup();
        }

        //tolgo il nodo uscente e metto quello entrante
        int exitingPosition = this.route.indexOf(exitingNode);
        this.route.set(exitingPosition, enteringNode);
    }

    /**
     * Fai lo scambio tra nodi contigui.
     * @param exitingNode
     * @param enteringNode
     */
    public void exchangeContiguousNodes(Node exitingNode, Node enteringNode) {
        double delta = getExchangeDelta(exitingNode, enteringNode);

        this.totalDistance += delta;

        int aPosition = route.indexOf(exitingNode);
        int bPosition = route.indexOf(enteringNode);

        route.set(aPosition, enteringNode);
        route.set(bPosition, exitingNode);
    }

    /**
     * Calcola il delta del costo ottenuto togliendo un nodo dalla rotta
     * @param exitingNode
     * @return
     */
    public double getNodeRemovalDelta(Node exitingNode) {
        int exitingPosition = this.route.indexOf(exitingNode);

        Node pred = this.route.get(exitingPosition - 1);
        Node succ = this.route.get(exitingPosition + 1);

        return -distances.getDistance(pred, exitingNode) - distances.getDistance(exitingNode, succ)
                + distances.getDistance(pred, succ);
    }

    /**
     * Toglie un nodo dalla rotta e aggiorna il carico
     * @param exitingNode
     */
    public void removeNode(Node exitingNode) {
        //Aggiora i carichi
        this.totalDistance += getNodeRemovalDelta(exitingNode);
        if (exitingNode instanceof DeliveryNode) {
            lhLoad -= ((DeliveryNode) exitingNode).getDelivery();
        } else if (exitingNode instanceof PickupNode) {
            bhLoad -= ((PickupNode) exitingNode).getPickup();
        }

        this.route.remove(exitingNode);
    }

    /**
     * Calcola il delta del costo ottenuto inserendo un nodo nella rotta
     * @param enteringNode
     * @param position
     * @return
     */
    public double getNodeInsertionDelta(Node enteringNode, int position) {

        Node pred = this.route.get(position - 1);
        Node succ = this.route.get(position);

        //controlla i carichi
        //se l'inserimento fa superare il carico, restituisci MAX_INT
        //in modo che non sia mai fatto

        if (enteringNode instanceof DeliveryNode) {
            DeliveryNode delEnterNode = (DeliveryNode) enteringNode;

            if (lhLoad + delEnterNode.getDelivery() > vehicleCapacity)
                return Integer.MAX_VALUE;
        } else {
            PickupNode pickEnterNode = (PickupNode) enteringNode;

            if (bhLoad + pickEnterNode.getPickup() > vehicleCapacity)
                return Integer.MAX_VALUE;
        }


        return -distances.getDistance(pred, succ) + distances.getDistance(pred, enteringNode) + distances.getDistance(enteringNode, succ);
    }

    /**
     * Inserisci un nodo alla posizione passata
     * @param enteringNode
     * @param position
     */
    public void insertNode(Node enteringNode, int position) {
        double delta = getNodeInsertionDelta(enteringNode, position);

        if (delta == Integer.MAX_VALUE)
            throw new RuntimeException("Tentativo non valido di inseire " + enteringNode + " nella posizione " + position + " in rotta " + this);

        this.totalDistance += delta;

        if (enteringNode instanceof DeliveryNode) {
            lhLoad += ((DeliveryNode) enteringNode).getDelivery();

        } else if (enteringNode instanceof PickupNode) {
            bhLoad += ((PickupNode) enteringNode).getPickup();
        }
        this.route.add(position, enteringNode);

    }

    /**
     * Restituisce la parte linehaul della rotta
     * @return
     */
    public List<Node> getLHList() {
        return route.stream().filter(n -> n instanceof DeliveryNode).collect(Collectors.toList());
    }


}
