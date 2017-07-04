package core.heuristics;

import core.cw.ClarkWright;
import core.model.DeliveryNode;
import core.model.Node;
import core.model.Route;
import core.model.WarehouseNode;

import java.util.List;

/**
 * Created by Sergio Serusi on 21/06/2017.
 */
public class BestRelocate {

    private static final double EPSILON = 0.000001;


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

    private static Choice checkRelocate(Route routeA, Route routeB, Node a, int position, Choice bestChoice) {
        double currentDelta = routeA.getNodeRemovalDelta(a) + routeB.getNodeInsertionDelta(a, position);

        //se è la scelta migliore
        if (bestChoice == null) {
            if (currentDelta + EPSILON < 0) {
                bestChoice = new Choice(a, position, routeB, currentDelta);
            }
        } else if (bestChoice.getValue() + EPSILON > currentDelta) {
            bestChoice = new Choice(a, position, routeB, currentDelta);
        }

        return bestChoice;
    }
}
