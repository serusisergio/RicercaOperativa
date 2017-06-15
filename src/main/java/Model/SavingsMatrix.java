package Model;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.*;


/**
 * Created by stefano on 12/06/17.
 */
public class SavingsMatrix {

    private final double OFFSET = 0.05;

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

                //non metto coppie riflessive ne simmetriche
                if (!first.equals(second) && !savings.containsKey(new Pair<>(nodes.get(j), nodes.get(i)))) {
                    double sav = distances.getDistance(first, warehouseNode) + distances.getDistance(warehouseNode, second) - distances.getDistance(first, second);
                    savings.put(new Pair<>(nodes.get(i), nodes.get(j)), sav);
                }
            }
        }
    }

    public List<Pair<Node, Node>> getSortedSaving() {
        List<Pair<Node, Node>> ordered = savings.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .map(pair -> pair.getKey())
                .collect(Collectors.toList());

        List<List<Pair<Node, Node>>> partitions = Lists.partition(ordered, (int) (ordered.size() * OFFSET));

        partitions.forEach(partition ->  Collections.shuffle(partition));

        return partitions.stream().flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
