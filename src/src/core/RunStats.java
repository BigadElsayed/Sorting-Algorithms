package core;

public class RunStats {
    private String algorithmName;
    private int arraySize;
    private String arraySource; // Random , Sorted , InverselySorted or fileName
    private int numberOfRuns;

    // in milliseconds
    private double avgRunTime;
    private double maxRunTime;
    private double minRunTime;

    private long comparisons;
    private long interchanges;

    public RunStats(String algorithmName, int arraySize, String arraySource, int numberOfRuns, double avgRunTime, double maxRunTime, double minRunTime, long comparisons, long interchanges) {
        this.algorithmName = algorithmName;
        this.arraySize = arraySize;
        this.arraySource = arraySource;
        this.numberOfRuns = numberOfRuns;
        this.avgRunTime = avgRunTime;
        this.maxRunTime = maxRunTime;
        this.minRunTime = minRunTime;
        this.comparisons = comparisons;
        this.interchanges = interchanges;
    }



    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getArraySize() {
        return arraySize;
    }

    public String getArraySource() {
        return arraySource;
    }

    public int getNumberOfRuns() {
        return numberOfRuns;
    }

    public double getAvgRunTime() {
        return avgRunTime;
    }

    public double getMaxRunTime() {
        return maxRunTime;
    }

    public double getMinRunTime() {
        return minRunTime;
    }

    public long getComparisons() {
        return comparisons;
    }

    public long getInterchanges() {
        return interchanges;
    }

    public String getAvgRunTimeFormatted() {
        return String.format("%.4f ms", avgRunTime);
    }
    public String getMaxRunTimeFormatted() {
        return String.format("%.4f ms", maxRunTime);
    }
    public String getMinRunTimeFormatted() {
        return String.format("%.4f ms", minRunTime);
    }

    @Override
    public String toString() {
        return "RunStats{" +
                "algorithmName='" + algorithmName + '\'' +
                ", arraySize=" + arraySize +
                ", arraySource='" + arraySource + '\'' +
                ", numberOfRuns=" + numberOfRuns +
                ", avgRunTime=" + avgRunTime +
                ", maxRunTime=" + maxRunTime +
                ", minRunTime=" + minRunTime +
                ", comparisons=" + comparisons +
                ", interchanges=" + interchanges +
                '}';
    }
}
