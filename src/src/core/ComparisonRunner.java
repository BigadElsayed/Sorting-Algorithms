package core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

// Here we use ExecutorService to make threads -> beacause this is java built_in thread pool which is much cleaner than raw threads

import algorithms.SortAlgorithm;
import algorithms.SortResult;

public class ComparisonRunner {

    public static RunStats run(SortAlgorithm sortAlgorithm ,
                               int[] inputArray,
                               String arraySource,
                               int numberOfRuns) throws ExecutionException, InterruptedException {
        if (numberOfRuns <= 0)
            throw new IllegalArgumentException("numberOfRuns must be >= 1");
        if (inputArray == null || inputArray.length == 0)
            throw new IllegalArgumentException("inputArray must not be null or empty");

        ExecutorService executor = Executors.newFixedThreadPool(numberOfRuns);
        List<Future<SortResult>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfRuns; i++) {
            final int[] copy = inputArray.clone();
            final SortAlgorithm instance = sortAlgorithm.newInstance();
            futures.add(executor.submit(() -> instance.sort(copy)));
        }

        List<SortResult> results = new ArrayList<>();
        for (Future<SortResult> future : futures) {
            results.add(future.get());  // blocks until this thread is done
        }
        executor.shutdown();

        double totalRunTime = 0;
        double maxRunTime = Double.MIN_VALUE;
        double minRunTime = Double.MAX_VALUE;
        long totalComparisons = 0 ;
        long totalInterchanges = 0 ;

        for (SortResult result : results) {
            totalRunTime += result.getRuntimeMs();
            maxRunTime = Math.max(maxRunTime, result.getRuntimeMs());
            minRunTime = Math.min(minRunTime, result.getRuntimeMs());
            totalComparisons += result.getComparisons();
            totalInterchanges += result.getInterchanges();
        }

        return new RunStats(
                sortAlgorithm.getAlgorithmName(),
                inputArray.length,
                arraySource,
                numberOfRuns,
                totalRunTime / numberOfRuns,   // avg rt
                maxRunTime,
                minRunTime,
                totalComparisons / numberOfRuns,
                totalInterchanges / numberOfRuns

                // here we use avg interchanges and comparisons So it will work
                // with all algos ( insertion , heap , merge , selection , bubble )
                // but issue was in quick sort because it uses random pivot so pivot changes
                // at each run so we can't take one of result
                // finally -> avg solve this problem
        );

    }

    // Run multiple algorithms on same input array
    public static List<RunStats> runAll(List<SortAlgorithm> algorithms,
                                        int[] inputArray,
                                        String arraySource,
                                        int numberOfRuns) throws ExecutionException, InterruptedException {
        List<RunStats> allStats = new ArrayList<>();
        for (SortAlgorithm algo : algorithms) {
            allStats.add(run(algo, inputArray, arraySource, numberOfRuns));
        }
        return allStats;
    }

    // run one algorithm on multiple input arrays
    public static List<RunStats> runOnMultipleInputs(SortAlgorithm algorithm,
                                                     int[][] inputArrays,
                                                     String[] sources,
                                                     int numberOfRuns) throws ExecutionException, InterruptedException {
        List<RunStats> allStats = new ArrayList<>();
        for (int i = 0; i < inputArrays.length; i++) {
            allStats.add(run(algorithm, inputArrays[i], sources[i], numberOfRuns));
        }
        return allStats;
    }


}
