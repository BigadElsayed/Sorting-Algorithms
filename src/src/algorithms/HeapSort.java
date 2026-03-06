package algorithms;

public class HeapSort extends SortAlgorithm {
    @Override
    protected void doSort(int[] array) {
        int heapSize = array.length;
        buildMaxHeap(array);
        while (heapSize > 1) {
            super.swap(array, heapSize - 1, 0);
            heapSize--;
            MaxHeapify(array, 0, heapSize);
        }
    }

    private void buildMaxHeap(int[] array) {
        for (int i = (array.length / 2) - 1; i >= 0; i--) {
            MaxHeapify(array, i, array.length);
        }
    }

    private void MaxHeapify(int[] array, int i, int heapSize) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        if (left < heapSize && super.compare(array, left, i) > 0) {
            largest = left;
        }
        if (right < heapSize && super.compare(array, right, largest) > 0) {
            largest = right;
        }
        if (largest != i) {
            super.swap(array, i, largest);
            MaxHeapify(array, largest, heapSize);
        }
    }

    @Override
    public String getAlgorithmName() {
        return "Heap Sort";
    }

    @Override
    public SortAlgorithm newInstance() { return new HeapSort(); }
}
