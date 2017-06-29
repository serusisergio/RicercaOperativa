package core.heuristics;

import core.model.Node;
import core.model.Route;

/**
 * Created by Sergio Serusi on 21/06/2017.
 */
public class Choice {
    private Node firstNode;
    private Node secondNode;
    private double value;
    private Route routeToChange;
    private int position;

    public Choice(Node a, Node b, Route r, double value){
        setFirstNode(a);
        setSecondNode(b);
        setValue(value);
        setRouteToChange(r);
    }

    public Choice(Node a, int position, Route r, double value){
        setFirstNode(a);
        this.position = position;
        setValue(value);
        setRouteToChange(r);
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

    public Route getRouteToChange() {
        return routeToChange;
    }

    public void setRouteToChange(Route routeToChange) {
        this.routeToChange = routeToChange;
    }

    public int getPosition() {
        return position;
    }
}
