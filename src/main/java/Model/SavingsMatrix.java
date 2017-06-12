package Model;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by stefano on 12/06/17.
 */
public class SavingsMatrix {
    private List<Node> nodes;
    private WarehouseNode warehouseNode;
    private DistanceMatrix distances;

    public Map<Pair<Node, Node>, Double> savings = new HashMap<>();


    public SavingsMatrix(List<Node> nodes, WarehouseNode warehouseNode, DistanceMatrix distances) {
        this.nodes = nodes;
        this.warehouseNode = warehouseNode;
        this.distances = distances;
        calculateMatrix();
    }

    private void calculateMatrix() {

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                Node first = nodes.get(i);
                Node second = nodes.get(j);
                if (!first.equals(second)) {
                    double sav = distances.getDistance(first, warehouseNode) + distances.getDistance(warehouseNode, second) - distances.getDistance(first, second);
                    savings.put(new Pair<>(nodes.get(i), nodes.get(j)), sav);
                }
            }
        }
    }

    public Map<Pair<Node, Node>, Double> getSortedSaving(){
        return sortByValue(savings);
    }

    /*
    * Ordina mappa
     */
    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
