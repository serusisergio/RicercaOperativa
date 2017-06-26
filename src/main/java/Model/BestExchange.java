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
        Node b = null;
        double value = 0;
        for(Route routeA : cr.getFinalRoutes()){
            for(Node a: routeA.getRoute()){
                for (Route routeB : cr.getFinalRoutes()) {
                    if (routeA != routeB) {//creare equals tra rotte

                        //se è la scelta migliore
                        if (bestChoice.getValue() < value) {
                            bestChoice = new BestChoice(a, b, value);
                        }


                    }
                }
            }
        }
    }
}
