package Assignment1;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class QuickSortAnalysis {
    private static Random random = new Random();

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        // Choose rightmost element as pivot
        int pivot = arr[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    // Randomized version for better average case
    private static void randomizedQuickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Choose random pivot
            int randomPivot = random.nextInt(high - low + 1) + low;
            // Swap random pivot with last element
            int temp = arr[randomPivot];
            arr[randomPivot] = arr[high];
            arr[high] = temp;
            
            int pi = partition(arr, low, high);
            randomizedQuickSort(arr, low, pi - 1);
            randomizedQuickSort(arr, pi + 1, high);
        }
    }

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("quick_sort_performance.csv")) {
            writer.write("size,best_case,average_case,worst_case\n");
            
            // Test different array sizes
            for (int size = 10; size <= 50000; size += 1000) {
                long bestCase = measureBestCase(size);
                long averageCase = measureAverageCase(size);
                long worstCase = measureWorstCase(size);
                
                writer.write(String.format("%d,%d,%d,%d\n", 
                    size, bestCase, averageCase, worstCase));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long measureBestCase(int size) {
        // Best case: Already sorted array with balanced partitions
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        long startTime = System.nanoTime();
        randomizedQuickSort(arr, 0, arr.length - 1);
        return System.nanoTime() - startTime;
    }

    private static long measureAverageCase(int size) {
        // Average case: Random array
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(size);
        }
        long startTime = System.nanoTime();
        randomizedQuickSort(arr, 0, arr.length - 1);
        return System.nanoTime() - startTime;
    }

    private static long measureWorstCase(int size) {
        // Worst case: Already sorted array with last element as pivot
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        long startTime = System.nanoTime();
        quickSort(arr, 0, arr.length - 1); // Using regular quicksort for worst case
        return System.nanoTime() - startTime;
    }
}