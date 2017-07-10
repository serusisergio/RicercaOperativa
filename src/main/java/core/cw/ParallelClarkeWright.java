package core.cw;

import core.model.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by stefano on 12/06/17.
 */
public class ParallelClarkeWright extends ClarkeWright {

    public ParallelClarkeWright(Instance i) {
        super(i);
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

        savings.getSortedSavings().forEach(pair -> {
            if (lhRoutes.size() > bhRoutes.size() && lhRoutes.size() > getInstance().getNumberVehicles()) {
                mergeRight(pair, lhRoutes);
            }
        });
    }

    @Override
    protected void solveBH() {
        //ottengo solo i nodi di pickup
        List<Node> bhNodes = getInstance().getNodesList().stream().filter(node -> node instanceof PickupNode).collect(Collectors.toList());

        //creo le rotte iniziali
        for (Node n : bhNodes) {
            Route r = new Route(getInstance().getWarehouseNode(), n, distances, getInstance().getVehiclesCapacity());
            bhRoutes.add(r);
        }

        savings.getSortedSavings().forEach(pair -> mergeRight(pair, bhRoutes));
    }


}