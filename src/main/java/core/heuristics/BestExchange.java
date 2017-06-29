package core.heuristics;

import core.model.DeliveryNode;
import core.model.Node;
import core.model.Route;
import core.model.WarehouseNode;
import core.cw.ClarkWright;

import java.util.List;

/**
 * Created by Sergio Serusi on 21/06/2017.
 */
public class BestExchange {

    public static void doBestExchanges(ClarkWright cr) {

        boolean exchangeDone;

        do {
            exchangeDone = false;

            for (Route routeA : cr.getFinalRoutes()) {  //Ciclo principale che fissato il nodo cerco il miglior scambio con tutti gli altri
                for (int i=0; i<routeA.getRoute().size(); i++) {

                    Node a = routeA.getRoute().get(i);

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

                                for (int j=0; j<otherList.size(); j++) {
                                    Node b = otherList.get(j);
                                    bestChoice = checkExchange(routeA, routeB, a, b, bestChoice);
                                }
                            }
                        }
                    }
                    //qui fai lo scambio
                    if (bestChoice != null) {
                        exchangeDone = true;
                        routeA.exchangeNodes(bestChoice.getFirstNode(), bestChoice.getSecondNode());
                        bestChoice.getRouteToChange().exchangeNodes(bestChoice.getSecondNode(), bestChoice.getFirstNode());
                    }

                }

            }
        } while (exchangeDone);
    }

    private static Choice checkExchange(Route routeA, Route routeB, Node a, Node b, Choice bestChoice) {
        double currentDelta = routeA.getExchangeDelta(a, b) + routeB.getExchangeDelta(b, a);

        //se è la scelta migliore
        if (bestChoice == null) {
            if (currentDelta < 0) {
                bestChoice = new Choice(a, b, routeB, currentDelta);
            }
        } else if (bestChoice.getValue() > currentDelta) {
            bestChoice = new Choice(a, b, routeB, currentDelta);
        }

        return bestChoice;
    }
}
