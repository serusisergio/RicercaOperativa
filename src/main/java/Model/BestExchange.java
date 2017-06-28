package Model;

import Solver.ClarkWright;

/**
 * Created by Sergio Serusi on 21/06/2017.
 */
public class BestExchange {
    ClarkWright cr;
    SavingsMatrix sv;
    BestChoice bestChoice;

    public BestExchange(ClarkWright cr){
        this.cr = cr;
        this.sv = cr.getSavings();
        findBestExchange();
    }


    void findBestExchange(){
        Route routeToChange;
        double value = 0;
        for(Route routeA : cr.getFinalRoutes()){  //Ciclo principale che fissato il nodo cerco il miglior scambio con tutti gli altri
            for(Node a: routeA.getRoute()){
                if(!(a instanceof WarehouseNode)) {
                    for (Route routeB : cr.getFinalRoutes()) { //ciclo che uso per scorrere e quindi confrontre con tutti gli altri nodi delle altre root

                        if (routeA != routeB) {//creare equals tra rotte
                            for (Node b : routeB.getRoute()) {
                                if(!(b instanceof WarehouseNode)) {
                                    //se è la scelta migliore
                                    if (bestChoice.getValue() < value) {
                                        bestChoice = new BestChoice(a, b, value);
                                        routeToChange = routeB;
                                    }
                                }
                            }


                        }
                    }
                }


            }
            //qui fai lo scambio

        }
    }
}
