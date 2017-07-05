package core.heuristics;

import core.cw.ClarkWright;
import core.model.*;

import java.util.List;

/**
 * Created by Sergio Serusi on 21/06/2017.
 */
public class BestRelocate {

    private static final double EPSILON = 0.01;

    /*
    public static void doBestRelocates(ClarkWright cr) {

        boolean relocateDone;

        int z=0;
        do {
            relocateDone = false;

            for (Route routeA : cr.getFinalRoutes()) {  //Ciclo principale che fissato il nodo cerco il miglior spostamento con tutti gli altri


                for (int i = 0; i < routeA.getRoute().size(); i++) {

                    Node a = routeA.getRoute().get(i);

                    //routeA e' la rotta dal quale sto togliendo il nodo
                    //se il nodo e' di tipo Delivery ed e' l'unico
                    //non faccio scambi, per evitare di lasciare rotte con solo parte backhaul
                    if (a instanceof DeliveryNode && routeA.getLHNodes().size() > 1) {
                        Choice bestChoice = null;

                        if (!(a instanceof WarehouseNode)) {

                            for (Route routeB : cr.getFinalRoutes()) { //ciclo che uso per scorrere e quindi confrontre con tutti gli altri nodi delle altre root

                                //if (routeA != routeB) {//creare equals tra rotte

                                    List<Node> otherList;
                                    if (a instanceof DeliveryNode) {
                                        otherList = routeB.getLHNodes();
                                    } else {
                                        //a è tipo Pickup
                                        otherList = routeB.getBHNodes();
                                    }

                                    //scorro tutte le posizioni nelle quali posso inserire il nodo a nella routeb
                                    if(otherList.size() > 1){
                                        //ottengo l'indice del primo Nodo della lista
                                        int start = routeB.getRoute().indexOf(otherList.get(0));
                                        //provo tutte le posizioni fino a quella successiva all'ultima posizione della lista
                                        //(perche' potrei mettere il nodo in coda)
                                        for (int position = start; position <= otherList.size(); position++) {
                                            bestChoice = checkRelocate(routeA, routeB, a, position, bestChoice);
                                        }
                                    }
                                //}
                            }
                        }
                        //qui fai lo scambio
                        if (bestChoice != null) {
                            relocateDone = true;

                            routeA.removeNode(bestChoice.getFirstNode());
                            bestChoice.getRouteToChange().insertNode(bestChoice.getFirstNode(), bestChoice.getPositionRouteA());
                        }
                    }

                }
                z++;
            }
            if(z>2)break;
        } while (relocateDone);
    }
    */

    public static void doBestRelocatesNew(ClarkWright cr) {
        List<Route> finalRoutes = cr.getFinalRoutes();
        Choice bestChoice = null;
        do {
            bestChoice = null;
            for (int i = 0; i < finalRoutes.size(); i++) {
                Route routeA = finalRoutes.get(i);

                List<Node> nodeRouteA = routeA.getRoute();
                for (int j = 0; j < nodeRouteA.size(); j++) {
                    Node nodeA = nodeRouteA.get(j);

                    for (int x = i + 1; x < finalRoutes.size(); x++) { //nn deve scambiare sulla stessa rotta
                        Route routeB = finalRoutes.get(x);

                        List<Node> nodeRouteB = routeB.getRoute();
                        for (int y = 0; y < nodeRouteB.size(); y++) {
                            Node nodeB = nodeRouteB.get(y);

                            if (nodeA instanceof DeliveryNode) {
                                if (nodeB instanceof DeliveryNode) {
                                        bestChoice = checkRelocate(routeA, routeB, i, x, y, nodeA, nodeB, bestChoice);
                                }
                            } else {
                                if (nodeA instanceof PickupNode) {
                                    if (nodeB instanceof PickupNode) {
                                        bestChoice = checkRelocate(routeA, routeB, i, x, y, nodeA, nodeB, bestChoice);
                                    }
                                }
                            }
                        }
                    }
                }

            }
            if (bestChoice != null) { //Fai lo scambio
                //System.out.println("Scambiando: "+bestChoice);
                Route routeAchange = finalRoutes.get(bestChoice.getPositionRouteA());
                Route routeBchange = finalRoutes.get(bestChoice.getPositionRouteB());

                //System.out.println("Scambiando: "+bestChoice);

                routeAchange.removeNode(bestChoice.getFirstNode());
                //System.out.println("Routeeee: "+routeAchange);
                //checkValidity(routeAchange,cr);

                routeBchange.insertNode(bestChoice.getSecondNode(),bestChoice.getPositionNodeB());
                //checkValidity(routeBchange,cr);

                finalRoutes.set(bestChoice.getPositionRouteA(), routeAchange);
                finalRoutes.set(bestChoice.getPositionRouteB(), routeBchange);
            }
        }while (bestChoice!=null);
        cr.setFinalRoutes(finalRoutes);


    }

    private static Choice checkRelocate(Route routeA, Route routeB,int posA,int posB,int position, Node a, Node b, Choice bestChoice) {
        double currentDelta = routeA.getNodeRemovalDelta(a) + routeB.getNodeInsertionDelta(a, position);

        if(currentDelta<0 && currentDelta>-EPSILON){
            currentDelta=0;
        }
        //se è la scelta migliore
        if (bestChoice == null) {
            if (currentDelta < 0) {
                bestChoice = new Choice(a, b,posA,posB,position, routeB, currentDelta);
            }
        } else if (bestChoice.getValue() > currentDelta) {
            //System.out.println("CurrenteDelta : "+currentDelta);
            //System.out.println("CurrenteValue : "+bestChoice.getValue());
            bestChoice = new Choice(a, b,posA,posB,position, routeB, currentDelta);
        }

        return bestChoice;
    }
}
