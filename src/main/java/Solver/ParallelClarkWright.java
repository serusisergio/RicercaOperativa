package Solver;

import Model.*;

import java.util.List;

/**
 * Created by stefano on 12/06/17.
 */
public class ParallelClarkWright {

    private Instance instance;

    public ParallelClarkWright(Instance i) {
        this.instance = i;

        mergeRoutes(solveLH(), solveBH());
    }

    private List<Route> solveLH() {


        return null;
    }

    private List<Route> solveBH() {
        return null;
    }

    private void mergeRoutes(List<Route> lh, List<Route> bh) {

    }
}
