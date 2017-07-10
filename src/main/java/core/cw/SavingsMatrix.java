package core.cw;

import core.model.Node;
import core.model.WarehouseNode;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.*;
import settings.Settings;


/**
 * Created by stefano on 12/06/17.
 */
public class SavingsMatrix {


    private List<Node> nodes;
    private WarehouseNode warehouseNode;
    private DistanceMatrix distances;

    private Map<Pair<Node, Node>, Double> savings = new HashMap<>();
    private List<Pair<Node, Node>> shuffledSavings;


    public SavingsMatrix(List<Node> nodes, WarehouseNode warehouseNode, DistanceMatrix distances) {
        this.nodes = nodes;
        this.warehouseNode = warehouseNode;
        this.setDistances(distances);
        computeMatrix();
    }

    private void computeMatrix() {

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                Node first = nodes.get(i);
                Node second = nodes.get(j);

                //non metto coppie riflessive ne simmetriche
                if (!first.equals(second) && !getSavings().containsKey(new Pair<>(nodes.get(j), nodes.get(i)))) {
                    double sav = getDistances().getDistance(first, warehouseNode) + getDistances().getDistance(warehouseNode, second) - getDistances().getDistance(first, second);
                    getSavings().put(new Pair<>(nodes.get(i), nodes.get(j)), sav);
                }
            }
        }
    }

    private void computeSavings() {
        //ottendo la lista dei savings ordinata
        List<Pair<Node, Node>> sortedSavings = getSavings().entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .map(pair -> pair.getKey())
                .collect(Collectors.toList());

        //divido la lista in partizioni di lunghezza dipendente dal parametro SHUFFLE_OFFSET
        List<List<Pair<Node, Node>>> partitions = Lists.partition(sortedSavings, (int) (sortedSavings.size() * Settings.SHUFFLE_OFFSET));

        //mischio in modo casuale le partizioni
        partitions.forEach(partition -> Collections.shuffle(partition));

        //unisco le partizioni in un'unica lista
        shuffledSavings = partitions.stream().flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * Metodo che restituisce i savings "circa" ordinati
     * il disordine dipende dal parametro SHUFFLE_OFFSET
     */
    public List<Pair<Node, Node>> getSortedSavings() {
        return shuffledSavings;
    }

    public DistanceMatrix getDistances() {
        return distances;
    }

    public void setDistances(DistanceMatrix distances) {
        this.distances = distances;
    }

    public Map<Pair<Node, Node>, Double> getSavings() {
        return savings;
    }


}
