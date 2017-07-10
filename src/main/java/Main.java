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

        //Per ogni istanza viene calcolato ClarkeWright sequenziale e parallelo, con mosse di BestRelocate e BestExchange
        //I risultati vengono scritti su "result.csv"
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("results.csv")))){

            bw.write("instance, parDiff, parTime, seqDiff, seqTime\n");

            //vengono lette le tutte le istanze, e per ogni istanza:
            for(Instance instance: fileManager.readInstances()) {

                System.out.println("Current instance: " + instance.getInstanceName());

                //Viene calcolato il Clarke Wright parallelo e scelta la soluzione migliore tra le ITERATION prove
                ClarkeWright pcw = getBestSolution(instance, true);
                pcw.saveToFile("solutions/parallel/" + instance.getInstanceName());

                //Viene calcolato il Clarke Wright sequenziale e scelta la soluzione migliore tra le ITERATION prove
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

    /**
     * Questo metodo riceve in ingresso una istanza ed un valore booleano.
     * Se il valore booleano è true viene eseguito ITERATIONS volte il calcolo di ClarkeWright parallelo seguito da mosse di
     * best relocate e best exchanges. Se invece il valore booleano è false viene eseguito ITERATIONS volte il calcolo sequenziale
     * di ClarkeWright seguito questa volta da mosse di best exchanges e best relocate. Infine viene preso il valore migliore
     * tre le ITETIONS volte che è stato eseguito
     * @param instance
     * @param parallel
     * @return
     */
    private static ClarkeWright getBestSolution(Instance instance, boolean parallel) {

        ClarkeWright bestSolution = null;
        double bestCost = Integer.MAX_VALUE;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < ITERATIONS; i++) {
            ClarkeWright cw;

            if (parallel) {
                cw = new ParallelClarkeWright(instance);
                BestRelocate.doBestRelocates(cw);
                BestExchange.doBestExchanges(cw);
            } else {
                cw = new SequentialClarkeWright(instance);
                BestExchange.doBestExchanges(cw);
                BestRelocate.doBestRelocates(cw);
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