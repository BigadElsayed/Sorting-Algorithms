package core;

import java.util.Random;

public class ArrayGenerator {
    public static final String RANDOM          = "Random";
    public static final String SORTED          = "Sorted";
    public static final String INVERSE_SORTED  = "Inversely Sorted";

    public static int[] generateArray(String type, int size) {
        if(size <= 0) {
            throw new IllegalArgumentException("Array size must be > 0");
        }
        else if(size > 10000) {
            throw new IllegalArgumentException("Array size must be < 10000");
        }
        else {
            int[] array = new int[size];
            switch (type) {
                case SORTED:
                    for (int i = 0; i < size; i++) {
                        array[i] = i + 1;
                    }
                    break;
                case INVERSE_SORTED:
                    for (int i = 0; i < size; i++) {
                        array[i] = size - i;
                    }
                    break;

                case RANDOM:
                    Random rand = new Random();
                    for (int i = 0; i < size; i++) {
                        array[i] = rand.nextInt(size * 10) + 1;  // values from 1 to size*10
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid type");
            }
            return array;
        }
    }

}
