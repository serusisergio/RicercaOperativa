package Model;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Edge {

    private Node first;
    private Node second;

    public double distance;

    public Edge(Node first, Node second, double distance) {
        this.first = first;
        this.second = second;
        this.distance = distance;
    }

    public Node getFirst() {
        return first;
    }

    public void setFirst(Node first) {
        this.first = first;
    }

    public Node getSecond() {
        return second;
    }

    public void setSecond(Node second) {
        this.second = second;
    }
}
