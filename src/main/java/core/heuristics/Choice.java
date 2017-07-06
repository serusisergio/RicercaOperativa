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
    private int positionSecondNode;
    private Route firstRoute;
    private Route secondRoute;


    //Used in relocate
    public Choice(Route firstRoute, Route secondRoute, Node firstNode, int positionSecondNode, double value) {
        this.firstRoute = firstRoute;
        this.secondRoute = secondRoute;
        this.firstNode = firstNode;
        this.positionSecondNode = positionSecondNode;
        this.value = value;
    }

    //Used in exchange
    public Choice(Route firstRoute, Route secondRoute, Node firstNode, Node secondNode, double value) {
        this.firstRoute = firstRoute;
        this.secondRoute = secondRoute;
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.value = value;
    }


    public Node getFirstNode() {
        return firstNode;
    }


    public Node getSecondNode() {
        return secondNode;
    }


    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        String toSt = new String();
        toSt += "FirstNode: " + firstNode;
        toSt += "SecondNode: " + secondNode;
        toSt += "Value: " + value;
        toSt += "FirstRoute: " + firstRoute;
        toSt += "SecondRoute: " + secondRoute;
        return toSt;
    }


    public int getPositionSecondNode() {
        return positionSecondNode;
    }


    public Route getFirstRoute() {
        return firstRoute;
    }

    public Route getSecondRoute() {
        return secondRoute;
    }

}
