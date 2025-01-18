package Assignment1;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MergeSortAnalysis {
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[mid + 1 + j];

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("merge_sort_performance.csv")) {
            writer.write("size,best_case,average_case,worst_case\n");
            
            // Test different array sizes
            for (int size = 5; size <= 50000; size += 100) {
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
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        long startTime = System.nanoTime();
        mergeSort(arr, 0, arr.length - 1);
        return System.nanoTime() - startTime;
    }

    private static long measureAverageCase(int size) {
        int[] arr = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(size);
        }
        long startTime = System.nanoTime();
        mergeSort(arr, 0, arr.length - 1);
        return System.nanoTime() - startTime;
    }

    private static long measureWorstCase(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }
        long startTime = System.nanoTime();
        mergeSort(arr, 0, arr.length - 1);
        return System.nanoTime() - startTime;
    }
}