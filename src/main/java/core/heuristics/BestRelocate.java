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
    public static void doBestRelocates(ClarkWright cr) {

        boolean relocateDone;

        do {
            relocateDone = false;

            for (Route routeA : cr.getFinalRoutes()) {  //Ciclo principale che fissato il nodo cerco il miglior spostamento con tutti gli altri


                for (int i = 0; i < routeA.getRoute().size(); i++) {

                    Node a = routeA.getRoute().get(i);

                    //routeA e' la rotta dal quale sto togliendo il nodo
                    //se il nodo e' di tipo Delivery ed e' l'unico
                    //non faccio scambi, per evitare di lasciare rotte con solo parte backhaul
                    if (a instanceof DeliveryNode && routeA.getLHNodes().size() == 1) {
                        Choice bestChoice = null;

                        if (!(a instanceof WarehouseNode)) {

                            for (Route routeB : cr.getFinalRoutes()) { //ciclo che uso per scorrere e quindi confrontre con tutti gli altri nodi delle altre root

                                if (routeA != routeB) {//creare equals tra rotte

                                    List<Node> otherList;
                                    if (a instanceof DeliveryNode) {
                                        otherList = routeB.getLHNodes();
                                    } else {
                                        //a è tipo Pickup
                                        otherList = routeB.getBHNodes();
                                    }

                                    //scorro tutte le posizioni nelle quali posso inserire il nodo a nella routeb

                                    int start = routeB.getRoute().indexOf(otherList.get(0));
                                    for (int position = start; position <= start + otherList.size(); position++) {
                                        bestChoice = checkRelocate(routeA, routeB, a, position, bestChoice);
                                    }
                                }
                            }
                        }
                        //qui fai lo scambio
                        if (bestChoice != null) {
                            relocateDone = true;

                            routeA.removeNode(bestChoice.getFirstNode());
                            bestChoice.getRouteToChange().insertNode(bestChoice.getFirstNode(), bestChoice.getPosition());
                        }
                    }

                }

            }
        } while (relocateDone);
    }

    private static Choice checkRelocate(Route routeA, Route routeB, Node a, int position, Choice bestChoice) {
        double currentDelta = routeA.getNodeRemovalDelta(a) + routeB.getNodeInsertionDelta(a, position);

        //se è la scelta migliore
        if (bestChoice == null) {
            if (currentDelta < 0) {
                bestChoice = new Choice(a, position, routeB, currentDelta);
            }
        } else if (bestChoice.getValue() > currentDelta) {
            bestChoice = new Choice(a, position, routeB, currentDelta);
        }

        return bestChoice;
    }
}
