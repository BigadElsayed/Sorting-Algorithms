

import algorithms.*;

public class Main {

    public static void main(String[] args) {

        // ----------------------------------------------------------------
        // Test arrays
        // ----------------------------------------------------------------
        int[] random         = {64, 25, 12, 22, 11, 90, 45, 33, 78, 5};
        int[] sorted         = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] inverseSorted  = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        // ----------------------------------------------------------------
        // Algorithms to test
        // ----------------------------------------------------------------
        SortAlgorithm[] algorithms = {
                new InsertionSort(),
                new SelectionSort(),
                new BubbleSort(),
                new QuickSort(),
                new MergeSort(),
                new HeapSort()
        };

        // ----------------------------------------------------------------
        // Run every algorithm on every array type
        // ----------------------------------------------------------------
        for (SortAlgorithm algo : algorithms) {
            System.out.println("=".repeat(55));
            System.out.println("  " + algo.getAlgorithmName());
            System.out.println("=".repeat(55));

            testSort(algo, "Random",         random);
            testSort(algo, "Sorted",         sorted);
            testSort(algo, "Inverse Sorted", inverseSorted);

            System.out.println();
        }
    }

    // ----------------------------------------------------------------
    // Helper: runs one algorithm on one array and prints the result
    // ----------------------------------------------------------------
    private static void testSort(SortAlgorithm algo, String label, int[] input) {

        System.out.println("\n  [ " + label + " ]");
        System.out.print("  Before: ");
        printArray(input);

        SortResult result = algo.sort(input);   // input is NOT modified (sort() clones it)

        System.out.print("  After:  ");
        printArray(result.getSortedArray());

        System.out.printf("  Runtime      : %.4f ms%n",  result.getRuntimeMs());
        System.out.printf("  Comparisons  : %d%n",       result.getComparisons());
        System.out.printf("  Interchanges : %d%n",       result.getInterchanges());
    }

    // ----------------------------------------------------------------
    // Helper: prints an int array on one line
    // ----------------------------------------------------------------
    private static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}