package core.heuristics;

import core.model.*;
import core.cw.ClarkWright;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Serusi on 21/06/2017.
 */
public class BestExchange {

    private static final double EPSILON = 0.01;

    private static Choice checkExchange(Route routeA, Route routeB, int posA, int posB, Node a, Node b, Choice bestChoice) {

        double currentDelta;
        if (routeA != routeB) { //da controllare
            currentDelta = routeA.getExchangeDelta(a, b) + routeB.getExchangeDelta(b, a);

        } else {
            //System.out.println("Stessa ROTTA, PoA: "+posA+"   PoB: "+posB);
            int pA = 0;
            int pB = 0;
            pA = routeA.getRoute().indexOf(a);
            pB = routeA.getRoute().indexOf(b);
            //System.out.println("Stessa ROTTA, PoA: "+pA+"   PoB: "+pB);
            if ((pA - pB) == 1 || (pA - pB) == -1) {//Controllare posizione nodo a e nodo b
                //System.out.println("CheckExchange- Consecutivi");
                currentDelta = routeA.getExchangeDelta(a, b);
                //System.out.println("CurrenteDelta : "+currentDelta);
            } else {
                currentDelta = routeA.getExchangeDelta(a, b) + routeA.getExchangeDelta(b, a);
            }

        }
        if (currentDelta < 0 && currentDelta > -EPSILON) {
            currentDelta = 0;
        }
        //se è la scelta migliore
        if (bestChoice == null) {
            if (currentDelta < 0) {
                //bestChoice = new Choice(a, b, posA, posB, routeB, currentDelta);
                bestChoice = new Choice(routeA, routeB, a, b, currentDelta);

            }
        } else if (bestChoice.getValue() > currentDelta) {
            //System.out.println("CurrenteDelta : "+currentDelta);
            //System.out.println("CurrenteValue : "+bestChoice.getValue());
            //bestChoice = new Choice(a, b, posA, posB, routeB, currentDelta);
            bestChoice = new Choice(routeA, routeB, a, b, currentDelta);
        }

        return bestChoice;
    }


    public static void doBestExchanges(ClarkWright cr) {
        List<Route> finalRoutes = cr.getFinalRoutes();
        Choice bestChoice = null;
        do {
            bestChoice = null;
            for (int i = 0; i < finalRoutes.size(); i++) {
                Route routeA = finalRoutes.get(i);

                List<Node> nodeRouteA = routeA.getRoute();
                for (int j = 0; j < nodeRouteA.size(); j++) {
                    Node nodeA = nodeRouteA.get(j);


                    for (int x = i; x < finalRoutes.size(); x++) {
                        Route routeB = finalRoutes.get(x);

                        List<Node> nodeRouteB = routeB.getRoute();

                        int y;
                        if (routeA == routeB) {
                            //se la rotta è la stessa
                            y = j + 1;
                        } else {
                            //se la rotta e' diversa guarda tutti i nodi
                            y = 0;
                        }

                        for (; y < nodeRouteB.size(); y++) {
                            Node nodeB = nodeRouteB.get(y);

                            //scarta le combinazioni con nodi di tipo diverso
                            if (nodeA instanceof DeliveryNode && nodeB instanceof DeliveryNode || nodeA instanceof PickupNode && nodeB instanceof PickupNode) {
                                bestChoice = checkExchange(routeA, routeB, i, x, nodeA, nodeB, bestChoice);
                            }

                        }
                    }
                }
            }
            if (bestChoice != null) { //Fai lo scambio
                Route routeAchange = bestChoice.getFirstRoute();
                Route routeBchange = bestChoice.getSecondRoute();

                if (bestChoice.getFirstRoute() == bestChoice.getSecondRoute()) {
                    routeAchange.exchangeContiguousNodes(bestChoice.getFirstNode(), bestChoice.getSecondNode());
                    finalRoutes.set(finalRoutes.indexOf(bestChoice.getFirstRoute()), routeAchange);

                } else {
                    routeAchange.exchangeNodes(bestChoice.getFirstNode(), bestChoice.getSecondNode());
                    routeBchange.exchangeNodes(bestChoice.getSecondNode(), bestChoice.getFirstNode());

                }


            }
        } while (bestChoice != null);

    }

}
