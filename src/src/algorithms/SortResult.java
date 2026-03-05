package algorithms;

import java.util.Arrays;

public class SortResult {
    private long runtimeNs;
    private long comparisons;
    private long interchanges;
    private int[] sortedArray;

    public SortResult(long runtimeNs, long comparisons, long interchanges, int[] sortedArray) {
        this.runtimeNs = runtimeNs;
        this.comparisons = comparisons;
        this.interchanges = interchanges;
        this.sortedArray = sortedArray.clone();
    }


    public long getRuntimeNs() {
        return runtimeNs;
    }

    public double getRuntimeMs() {
        return runtimeNs / 1000000.0;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getInterchanges() {
        return interchanges;
    }

    public int[] getSortedArray() {
        return sortedArray.clone();
    }

    @Override
    public String toString() {
        return "SortResult{" +
                "runtimeNs=" + runtimeNs +
                ", comparisons=" + comparisons +
                ", interchanges=" + interchanges +
                ", sortedArray=" + Arrays.toString(sortedArray) +
                '}';
    }
}