package core.cw;

import core.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by stefano on 12/06/17.
 */
public class SequentialClarkeWright extends ClarkeWright {

    public SequentialClarkeWright(Instance i) {
        super(i);
    }

    @Override
    protected void solveBH() {
        //ottengo solo i nodi di delivery
        List<Node> bhNodes = getInstance().getNodesList().stream().filter(node -> node instanceof PickupNode).collect(Collectors.toList());

        //creo le rotte iniziali
        for (Node n : bhNodes) {
            Route r = new Route(getInstance().getWarehouseNode(), n, distances, getInstance().getVehiclesCapacity());
            bhRoutes.add(r);
        }


        List<Route> temp = new ArrayList<>(bhRoutes);
        bhRoutes.forEach(route ->
                savings.getSortedSavings().forEach(pair  -> {
                    mergeRight(pair, temp);
                    mergeLeft(pair, temp);
                })
        );

        bhRoutes = temp;

    }

    @Override
    protected void solveLH() {
        //ottengo solo i nodi di delivery
        List<Node> lhNodes = getInstance().getNodesList().stream().filter(node -> node instanceof DeliveryNode).collect(Collectors.toList());

        //creo le rotte iniziali
        for (Node n : lhNodes) {
            Route r = new Route(getInstance().getWarehouseNode(), n, distances, getInstance().getVehiclesCapacity());
            lhRoutes.add(r);
        }

        List<Route> temp = new ArrayList<>(lhRoutes);
        lhRoutes.forEach(route ->
            savings.getSortedSavings().forEach(pair -> {
                if (temp.size() > bhRoutes.size() && temp.size() > getInstance().getNumberVehicles()) {
                    mergeRight(pair, temp);
                    mergeLeft(pair, temp);
                }
            })
        );

        lhRoutes = temp;
    }

}
