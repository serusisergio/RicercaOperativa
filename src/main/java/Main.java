import core.cw.ParallelClarkeWright;
import core.cw.SequentialClarkeWright;
import core.heuristics.BestExchange;
import core.heuristics.BestRelocate;
import core.model.*;
import resourcesManager.FileManager;
import core.cw.ClarkeWright;

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

                ClarkeWright pcw = getBestSolution(instance, true);
                pcw.saveToFile("solutions/parallel/" + instance.getInstanceName());

                ClarkeWright scw = getBestSolution(instance, false);
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

    private static ClarkeWright getBestSolution(Instance instance, boolean parallel) {

        ClarkeWright bestSolution = null;
        double bestCost = Integer.MAX_VALUE;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < ITERATIONS; i++) {
            ClarkeWright cw;

            if (parallel) {
                cw = new ParallelClarkeWright(instance);
                BestRelocate.doBestRelocatesNew(cw);
                BestExchange.doBestExchanges(cw);
            } else {
                cw = new SequentialClarkeWright(instance);
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