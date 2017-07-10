package core.heuristics;

import core.model.*;
import core.cw.ClarkeWright;
import settings.Settings;

import java.util.List;

/**
 * Created by Sergio Serusi on 21/06/2017.
 */
public class BestExchange {

    public static void doBestExchanges(ClarkeWright cr) {
        List<Route> finalRoutes = cr.getFinalRoutes();
        Choice bestChoice = null;

        //controllo se ci sono scambi da fare
        //il ciclo termina quando ho controllato tutti gli scambi possibili
        //e non ne ho fatto nemmeno uno
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


    private static Choice checkExchange(Route routeA, Route routeB, int posA, int posB, Node a, Node b, Choice bestChoice) {

        double currentDelta;

        if (routeA != routeB) {
            //se le rotte sono diverse il delta e' la somma dei delta delle rotte
            currentDelta = routeA.getExchangeDelta(a, b) + routeB.getExchangeDelta(b, a);

        } else {
            //se la rotta e' la stessa
            int pA = routeA.getRoute().indexOf(a);
            int pB = routeA.getRoute().indexOf(b);

            //se i nodi sono contigui
            if (Math.abs(pA - pB) == 1) {

                //il delta e' da calcolare una sola volta
                currentDelta = routeA.getExchangeDelta(a, b);
            } else {

                //se non lo sono, il calcolo e' uguale al caso di rotte diverse
                //(a parte per l'uso della stessa rotta
                currentDelta = routeA.getExchangeDelta(a, b) + routeA.getExchangeDelta(b, a);
            }

        }

        if (currentDelta < 0 && currentDelta > -Settings.EPSILON) {
            currentDelta = 0;
        }

        //se è la scelta migliore
        if (bestChoice == null) {
            if (currentDelta < 0) {
                bestChoice = new Choice(routeA, routeB, a, b, currentDelta);

            }
        } else if (bestChoice.getValue() > currentDelta) {
            bestChoice = new Choice(routeA, routeB, a, b, currentDelta);
        }

        return bestChoice;
    }

}
