package algorithms;

public class MergeSort extends SortAlgorithm {
    @Override
    protected void doSort(int[] array) {
        mergeSort(array, 0, array.length - 1);
    }

    private void merge(int[] array, int left, int mid, int right) {

        int[] leftArray = new int[mid - left + 1];
        int[] rightArray = new int[right - mid];

        for (int k = 0; k < leftArray.length; k++) {
            leftArray[k] = array[left + k];
        }
        for (int k = 0; k < rightArray.length; k++) {
            rightArray[k] = array[mid + k + 1];
        }

        int start1 = 0;
        int start2 = 0;

        int i = left;

        while (start1 < leftArray.length && start2 < rightArray.length) {
            if (super.compareValue(leftArray[start1], rightArray, start2) < 0) {
                super.move(array, i, leftArray[start1]);
                start1++;
            } else {
                super.move(array, i, rightArray[start2]);
                start2++;
            }
            i++;
        }
        while (start1 < leftArray.length) {
            super.move(array, i, leftArray[start1]);
            start1++;
            i++;
        }
        while (start2 < rightArray.length) {
            super.move(array, i, rightArray[start2]);
            start2++;
            i++;
        }

    }

    private void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);

            merge(array, left, mid, right);
        }
    }

    @Override
    public String getAlgorithmName() {
        return "Merge Sort";
    }
}
