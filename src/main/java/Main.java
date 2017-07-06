import core.cw.ParallelClarkWright;
import core.cw.SequentialClarkWright;
import core.heuristics.BestExchange;
import core.heuristics.BestRelocate;
import core.model.*;
import resourcesManager.FileManager;
import core.cw.ClarkWright;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Sergio Serusi on 07/06/2017.
 */
public class Main {

    private static final int ITERATIONS = 10;
    private static final boolean BENCHMARK = false;

    public static void main(String[] args) {

        FileManager fileManager = new FileManager();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("results.csv")))){

            bw.write("instance, parDiff, parTime, seqDiff, seqTime\n");

            for(Instance instance: fileManager.readInstances()) {

                System.out.println("Current instance: " + instance.getInstanceName());

                ClarkWright pcw = getBestSolution(instance, true);
                pcw.saveToFile("solutions/parallel/" + instance.getInstanceName());

                ClarkWright scw = getBestSolution(instance, false);
                scw.saveToFile("solutions/sequential/" + instance.getInstanceName());

                String name = instance.getInstanceName().replace(".txt", "");
                bw.write(makeCsvLine(name, pcw.getSolutionMargin(), pcw.getTime(), scw.getSolutionMargin(), scw.getTime()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String makeCsvLine(String name, double parallelDiff, double parallelTime, double sequentialDiff, double sequentialTime){
        return name + ", " + parallelDiff + ", " + parallelTime + ", " + sequentialDiff + ", " + sequentialTime + "\n";
    }

    private static ClarkWright getBestSolution(Instance instance, boolean parallel) {

        ClarkWright bestSolution = null;
        double bestCost = Integer.MAX_VALUE;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < ITERATIONS; i++) {
            ClarkWright cw;

            if (parallel) {
                cw = new ParallelClarkWright(instance);
                BestRelocate.doBestRelocatesNew(cw);
                BestExchange.doBestExchanges(cw);
            } else {
                cw = new SequentialClarkWright(instance);
                BestExchange.doBestExchanges(cw);
                BestRelocate.doBestRelocatesNew(cw);
            }


            if (cw.getTotalCost() < bestCost) {
                bestCost = cw.getTotalCost();
                bestSolution = cw;
            }
        }

        double time = (System.currentTimeMillis() - startTime) / 1000.0;
        bestSolution.setTime(time);

        if (BENCHMARK)
            System.out.println("Total execution time: " + time);

        return bestSolution;
    }


}