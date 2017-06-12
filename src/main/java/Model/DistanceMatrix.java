package Model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by saimon on 08/06/17.
 */
public class DistanceMatrix {
    private List<Node> nodes;

    public Map<Pair<Node, Node>, Double> matrix;

    public DistanceMatrix(List<Node> nodes, Node warehouse){
        this.nodes = nodes;
        this.nodes.add(warehouse);
        calculateMatrix();
    }

    private void calculateMatrix(){

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                matrix.put(new Pair<>(nodes.get(i), nodes.get(j)), calculateDistance(nodes.get(i), nodes.get(j)));
            }
        }
    }

    public static double calculateDistance(Node first, Node second){
        return Math.sqrt(Math.pow(first.getX()-second.getX(), 2) + Math.pow(first.getY()-second.getY(), 2));
    }

    public double getDistance(Node first, Node second){
        return matrix.get(new Pair<>(first, second));
    }
}
