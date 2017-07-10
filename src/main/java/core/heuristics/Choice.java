package core.heuristics;

import core.model.Node;
import core.model.Route;

/**
 * Questa classe viene utilizzata per salvare la scelta migliore fino al momento
 */
public class Choice {

    private Node firstNode;         //Conterra il Nodo da spostare (nel caso di exchange verra scambiato con il secondo nodo)
    private Node secondNode;        //Conterra il Nodo da spostare (nel caso di exchange verra scambiato con il primo nodo)
    private double value;           //Conterrà il guadagno che si otterrà dallo scambio
    private int positionSecondNode; //Contiene la posizione del second node, utile per poterci inserire FirstNode quando si fa relocate
    private Route firstRoute;       //Contiene la first route, in cui andare ad inserire il secondo node per exchange.
    private Route secondRoute;      //Contiene la second route, in cui andare ad inserire il first node per exchange.


    /**
     * Questo costruttore viene utilizzato per salvare la migliore scelta con Relocate. Infatti, salviamo first route che contiene
     * il nodo da spostare, second route è la route in cui inserire first node nella posizione "positionSecondNode". Value contiene
     * il che si ottiene con la mossa
     * @param firstRoute
     * @param secondRoute
     * @param firstNode
     * @param positionSecondNode
     * @param value
     */
    public Choice(Route firstRoute, Route secondRoute, Node firstNode, int positionSecondNode, double value) {
        this.firstRoute = firstRoute;
        this.secondRoute = secondRoute;
        this.firstNode = firstNode;
        this.positionSecondNode = positionSecondNode;
        this.value = value;
    }

    /**
     * Questo costruttore viene utilizzato per salvare la migliore scelta con exchange. Infatti, salviamo first route che contiene
     * first node che verra inserito al posto di second node nella second route, e verrà sostituito da second node della second route
     * Value contiene il guadagno che si ottiene dalla mossa
     * @param firstRoute
     * @param secondRoute
     * @param firstNode
     * @param secondNode
     * @param value
     */
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

    public Route getSecondRoute() { return secondRoute;}

}
