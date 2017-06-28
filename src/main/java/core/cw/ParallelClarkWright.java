package core.cw;

import core.model.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by stefano on 12/06/17.
 */
public class ParallelClarkWright extends ClarkWright {

    public ParallelClarkWright(Instance i) {
        super(i);
    }

    @Override
    protected void solveLH() {
        //ottengo solo i nodi di delivery
        List<Node> lhNodes = instance.getNodesList().stream().filter(node -> node instanceof DeliveryNode).collect(Collectors.toList());

        //creo le rotte iniziali
        for (Node n : lhNodes) {
            Route r = new Route(instance.getWarehouseNode(), n, distances, instance.getVehiclesCapacity());
            lhRoutes.add(r);
        }

        savings.getSortedSaving().forEach(pair -> {
            if (lhRoutes.size() > bhRoutes.size() && lhRoutes.size() > instance.getNumberVehicles()) {
                mergeRight(pair, lhRoutes);
            }
        });
    }

    @Override
    protected void solveBH() {
        //ottengo solo i nodi di pickup
        List<Node> bhNodes = instance.getNodesList().stream().filter(node -> node instanceof PickupNode).collect(Collectors.toList());

        //creo le rotte iniziali
        for (Node n : bhNodes) {
            Route r = new Route(instance.getWarehouseNode(), n, distances, instance.getVehiclesCapacity());
            bhRoutes.add(r);
        }

        savings.getSortedSaving().forEach(pair -> mergeRight(pair, bhRoutes));
    }


}