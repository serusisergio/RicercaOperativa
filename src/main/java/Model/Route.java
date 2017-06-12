package Model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Route {

    private double totalDistance = 0;

    private List<Edge> route = new ArrayList<>();

    public Route(){}

    public Route(Node warehouse, Node node, DistanceMatrix matrix){
        double distance = matrix.getDistance(warehouse, node);
        Edge up = new Edge(warehouse, node, distance);
        Edge down = new Edge(node, warehouse, distance);

        route.add(up);
        route.add(down);
        totalDistance = distance*2;
    }
}
