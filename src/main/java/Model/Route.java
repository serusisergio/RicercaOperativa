package Model;


import java.util.ArrayList;
import java.util.List;

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

    public Route() {
    }

    public Route(Node warehouse, Node node, DistanceMatrix distances, int vehicleCapacity) {
        this.distances = distances;
        this.vehicleCapacity = vehicleCapacity;
        double distance = distances.getDistance(warehouse, node);

        route.add(warehouse);
        route.add(node);
        route.add(warehouse);

        totalDistance = distance * 2;

        if(node instanceof DeliveryNode){
            lhLoad += ((DeliveryNode) node).getDelivery();
        } else {
            bhLoad += ((PickupNode) node).getPickup();
        }
    }

    public void mergeEnd(Route r) {


        //check if the merge is allowed
        if(this.bhLoad + r.bhLoad > vehicleCapacity)
            throw new LoadExceededException();

        if(this.lhLoad + r.lhLoad > vehicleCapacity)
            throw new LoadExceededException();


        //taglia le rotte
        Node last = this.route.remove(this.route.size() - 1);
        if (!(last instanceof WarehouseNode))
            throw new RuntimeException("Rotta che non finisce in warehouse!");

        Node first = r.route.remove(0);
        if (!(first instanceof WarehouseNode))
            throw new RuntimeException("Rotta che non inizia in warehouse!");

        Node newLast = this.route.get(this.route.size() - 1);
        Node newFirst  = r.route.get(0);

        double newEdgeDistance = distances.getDistance(newFirst, newLast);

        //aggiorno la rotta
        this.route.addAll(r.route);


        //aggiorno la distanza
        this.totalDistance -= distances.getDistance(newLast, last);
        this.totalDistance -= distances.getDistance(newFirst, first);
        this.totalDistance += newEdgeDistance;

        //aggiorno i carichi
        this.bhLoad += r.bhLoad;
        this.lhLoad += r.lhLoad;
    }

    public void mergeStart(Route r) {

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
    public String toString(){
        return route.toString();
    }
}
