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
    private int positionRouteA;
    private int positionRouteB;
    private int positionNodeB;


    public Choice(Node a, Node b, Route r, double value){
        setFirstNode(a);
        setSecondNode(b);
        setValue(value);
        setRouteToChange(r);
    }

    public Choice(Node a, int positionA, Route r, double value){
        setFirstNode(a);
        this.positionRouteA = positionA;
        setValue(value);
        setRouteToChange(r);
    }

    public Choice(Node a, Node b,int positionA,int positionB, Route r, double value){
        setFirstNode(a);
        setSecondNode(b);
        setValue(value);
        setRouteToChange(r);
        this.positionRouteA = positionA;
        this.setPositionRouteB(positionB);
    }

    public Choice(Node a, Node b,int positionA,int positionB,int positionNodeB, Route r, double value){
        setFirstNode(a);
        setSecondNode(b);
        setValue(value);
        setRouteToChange(r);
        this.positionRouteA = positionA;
        this.setPositionRouteB(positionB);
        this.setPositionNodeB(positionNodeB);
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

    public int getPositionRouteA() {
        return positionRouteA;
    }

    @Override
    public String toString(){
        String toSt = new String();
        toSt+="    FirstNode: "+firstNode;
        toSt+=("   SecondNode: "+secondNode);
        toSt+=("   Value: "+value);
        toSt+=("   RouteToChange: "+routeToChange);
        return toSt;
    }

    public int getPositionRouteB() {
        return positionRouteB;
    }

    public void setPositionRouteB(int positionRouteB) {
        this.positionRouteB = positionRouteB;
    }

    public int getPositionNodeB() {
        return positionNodeB;
    }

    public void setPositionNodeB(int positionNodeB) {
        this.positionNodeB = positionNodeB;
    }
}
