package Solver;

import Model.*;

import java.util.List;

/**
 * Created by stefano on 12/06/17.
 */
public class SequencialClarkWright {

    private Instance instance;
    private DistanceMatrix distances;

    public SequencialClarkWright(Instance i) {
        this.instance = i;
        this.distances = new DistanceMatrix(i.getNodesList(), i.getWarehouseNode());

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
