package core.heuristics;

import core.cw.ClarkWright;
import core.model.*;

import java.util.List;

/**
 * Created by Sergio Serusi on 21/06/2017.
 */
public class BestRelocate {

    private static final double EPSILON = 0.01;


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

                            //scarta le combinazioni con nodi di tipo diverso
                            if (nodeA instanceof DeliveryNode && nodeB instanceof DeliveryNode || nodeA instanceof PickupNode && nodeB instanceof PickupNode) {
                                bestChoice = checkRelocate(routeA, routeB, nodeA, y, bestChoice);
                            }

                        }
                    }
                }

            }
            if (bestChoice != null) { //Fai lo scambio
                Route routeAchange = bestChoice.getFirstRoute();
                Route routeBchange = bestChoice.getSecondRoute();

                routeAchange.removeNode(bestChoice.getFirstNode());
                routeBchange.insertNode(bestChoice.getFirstNode(), bestChoice.getPositionSecondNode());

            }
        } while (bestChoice != null);

    }

    private static Choice checkRelocate(Route routeA, Route routeB, Node a, int position, Choice bestChoice) {
        double currentDelta = routeA.getNodeRemovalDelta(a) + routeB.getNodeInsertionDelta(a, position);

        if (currentDelta < 0 && currentDelta > -EPSILON) {
            currentDelta = 0;
        }
        //se è la scelta migliore
        if (bestChoice == null) {
            if (currentDelta < 0) {
                bestChoice = new Choice(routeA, routeB, a, position, currentDelta);
            }
        } else if (bestChoice.getValue() > currentDelta) {

            bestChoice = new Choice(routeA, routeB, a, position, currentDelta);


        }

        return bestChoice;
    }
}
