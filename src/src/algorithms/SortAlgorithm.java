package algorithms;

import java.util.ArrayList;
import java.util.List;

public abstract class SortAlgorithm {

    protected long comparisons;
    protected long interchanges;

    private boolean recSteps ;
    private List<int[]> steps ;

    // When we compare any 2 elements , move any element or swap 2 elements
    // we want their color to change so we use indices of this List

    private List<int[]> highlightedIndices;

    abstract protected void doSort(int[] array);
    abstract public String getAlgorithmName();
    public abstract SortAlgorithm newInstance();

    public void resetCounters(){
        comparisons = 0;
        interchanges = 0;
    }

    public SortResult sort(int[] array){
        resetCounters();
        recSteps = false;

        int[] arr = array.clone();

        long startTime = System.nanoTime();
        doSort(arr);
        long endTime = System.nanoTime();

        return new SortResult(endTime-startTime,comparisons,interchanges,arr);
    }

    public SortResult sortWithSteps(int[] array){
        resetCounters();
        recSteps = true;

        steps = new ArrayList<int[]>();
        highlightedIndices = new ArrayList<>();

        int[] arr = array.clone();

        // initial state
        steps.add(arr.clone());
        highlightedIndices.add(new int[]{-1 , -1});

        long startTime = System.nanoTime();
        doSort(arr);
        long endTime = System.nanoTime();

        // final state
        steps.add(arr.clone());
        highlightedIndices.add(new int[]{-1 , -1});

        return new SortResult(endTime-startTime,comparisons,interchanges,arr);

    }

    public List<int[]> getSteps() {
        if(steps != null) {
            return steps;
        }
        return new ArrayList<>();
    }
    public List<int[]> getHighlightedIndices() {
        if(highlightedIndices != null) {
            return highlightedIndices;
        }
        return new ArrayList<>();
    }

    // Here we will make protected helpers ( swap , move , compareArrayVals , compareArrayValwithexternalVal )
    // so subclasses must use them instead of raw operators
    // to handle comparisons and interchanges

    protected int compare(int[] array1, int idx1 , int idx2){
        comparisons++;
        return Integer.compare(array1[idx1],array1[idx2]);
    }

    protected int compareValue(int val , int[] array1, int idx1){
        comparisons++;
        return Integer.compare(val , array1[idx1]);
    }

    protected void swap(int[] array1, int idx1, int idx2){
        interchanges++;

        int temp = array1[idx1];
        array1[idx1] = array1[idx2];
        array1[idx2] = temp;

        if(recSteps){
            steps.add(array1.clone());
            highlightedIndices.add(new int[]{idx1,idx2});
        }
    }

    protected void move(int[] array1, int destination, int value){
        interchanges ++ ;
        array1[destination] = value;
        if(recSteps){
            steps.add(array1.clone());
            highlightedIndices.add(new int[]{destination,-1});
        }
    }
}
