package core.cw;

import core.model.Node;

import java.util.List;

/**
 * Created by saimon on 08/06/17.
 */
public class DistanceMatrix {
    private List<Node> nodes;

    private double matrix[][];


    public DistanceMatrix(List<Node> nodes, Node warehouse){
        this.nodes = nodes;
        this.nodes.add(warehouse);
        this.matrix = new double[nodes.size()+1][nodes.size()+1];

        calculateMatrix();
    }

    private void calculateMatrix(){

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                matrix[nodes.get(i).getId()][nodes.get(j).getId()] = calculateDistance(nodes.get(i), nodes.get(j));
            }
        }
    }

    public static double calculateDistance(Node first, Node second){
        return Math.sqrt(Math.pow(first.getX()-second.getX(), 2) + Math.pow(first.getY()-second.getY(), 2));
    }

    public double getDistance(Node first, Node second){
        return matrix[first.getId()][second.getId()];
    }
}
