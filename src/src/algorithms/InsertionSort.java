package algorithms;

public class InsertionSort extends SortAlgorithm {

    @Override
    protected void doSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && super.compareValue(key, array, j) < 0) {
                super.move(array, j + 1, array[j]);
                j = j - 1;
            }
            super.move(array, j + 1, key);
        }
    }

    @Override
    public String getAlgorithmName() {
        return "Insertion Sort";
    }
}
