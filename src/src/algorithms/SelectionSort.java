package algorithms;

public class SelectionSort extends SortAlgorithm {

    @Override
    protected void doSort(int[] array) {
        for (int i = 0; i < array.length ; i++) {
            int min_idx = i ;
            for (int j = i+1; j < array.length ; j++) {
                if( super.compare(array , min_idx, j) > 0) {
                    min_idx = j;
                }
            }
            if(min_idx != i) {
                swap(array, min_idx, i);
            }
        }
    }

    @Override
    public String getAlgorithmName() {
        return "Selection Sort";
    }
}
