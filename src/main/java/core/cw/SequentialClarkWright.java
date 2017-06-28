package core.cw;

import core.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by stefano on 12/06/17.
 */
public class SequentialClarkWright extends ClarkWright {

    public SequentialClarkWright(Instance i) {
        super(i);
    }

    @Override
    protected void solveBH() {
        //ottengo solo i nodi di delivery
        List<Node> bhNodes = instance.getNodesList().stream().filter(node -> node instanceof PickupNode).collect(Collectors.toList());

        //creo le rotte iniziali
        for (Node n : bhNodes) {
            Route r = new Route(instance.getWarehouseNode(), n, distances, instance.getVehiclesCapacity());
            bhRoutes.add(r);
        }


        List<Route> temp = new ArrayList<>(bhRoutes);
        bhRoutes.forEach(route ->
                savings.getSortedSaving().forEach(pair  -> {
                    mergeRight(pair, temp);
                    mergeLeft(pair, temp);
                })
        );

        bhRoutes = temp;

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

        List<Route> temp = new ArrayList<>(lhRoutes);
        lhRoutes.forEach(route ->
            savings.getSortedSaving().forEach(pair -> {
                if (temp.size() > bhRoutes.size() && temp.size() > instance.getNumberVehicles()) {
                    mergeRight(pair, temp);
                    mergeLeft(pair, temp);
                }
            })
        );

        lhRoutes = temp;
    }

}
