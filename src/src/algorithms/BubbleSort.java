package algorithms;

public class BubbleSort extends SortAlgorithm {

    @Override
    protected void doSort(int[] array) {
        boolean swapped = false;
        for (int i = 0; i < array.length ; i++) {
            for (int j = 0 ; j < array.length - 1 - i ; j++) {
                if (super.compare(array , j , j + 1) > 0){
                    super.swap(array , j + 1   , j);
                    swapped = true;
                }
            }
            // Already sorted Array
            // More Optimization
            if (!swapped){
                break;
            }
        }
    }

    @Override
    public String getAlgorithmName() {
        return "Bubble Sort";
    }
}