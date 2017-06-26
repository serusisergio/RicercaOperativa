package Model;

/**
 * Created by Sergio Serusi on 21/06/2017.
 */
public class BestChoice {
    private Node firstNode;
    private Node secondNode;
    private double value;

    BestChoice(Node a, Node b, double value){
        setFirstNode(a);
        setSecondNode(b);
        setValue(value);

    }

    public Node getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(Node firstNode) {
        this.firstNode = firstNode;
    }

    public Node getSecondNode() {
        return secondNode;
    }

    public void setSecondNode(Node secondNode) {
        this.secondNode = secondNode;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
