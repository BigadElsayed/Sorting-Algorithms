package algorithms;

public class QuickSort extends SortAlgorithm {

    private int partition(int[] array, int low, int high) {
        int pivot = array[low];
        int i = low;
        int j = low + 1;

        while (j <= high) {
            if (super.compareValue(pivot, array, j) > 0) {
                i++;
                if (i != j) {
                    super.swap(array, i, j);
                }
            }
            j++;
        }
        if (i != low) {
            super.swap(array, i, low);
        }
        return i;
    }

    private int randomPartition(int[] array, int low, int high) {
        int randomIdx = low + (int) (Math.random() * (high - low + 1));
        super.swap(array, low, randomIdx);
        return partition(array, low, high);
    }

    private void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivot = randomPartition(array, low, high);
            quickSort(array, low, pivot - 1);
            quickSort(array, pivot + 1, high);
        }
    }


    @Override
    protected void doSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }


    @Override
    public String getAlgorithmName() {
        return "Quick Sort";
    }

    @Override
    public SortAlgorithm newInstance() { return new QuickSort(); }
}
