package Assignment1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class MergeSort {

    static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];

        for (int i = 0; i < n1; ++i)
            leftArr[i] = arr[left + i];
        for (int j = 0; j < n2; ++j)
            rightArr[j] = arr[mid + 1 + j];

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }

    static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    static int[] generateRandomArray(int size, int maxValue) {
        int[] arr = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(maxValue);
        }
        return arr;
    }



    static void generateData(int maxSize, String filename, int maxValue, int numRuns) {
        try (PrintWriter outputFile = new PrintWriter(new FileWriter(filename))) {
              outputFile.println("Input Size,Average Time (nanoseconds)");

            for (int size = 10; size <= maxSize; size += 10) {
                long totalTime = 0;
                for (int run = 0; run < numRuns; run++) {
                   int[] arr = generateRandomArray(size, maxValue);
                    long startTime = System.nanoTime();
                     mergeSort(arr, 0, size - 1);
                   long endTime = System.nanoTime();
                    totalTime += (endTime - startTime);
                }
                long averageTime = totalTime / numRuns;
                outputFile.println(size + "," + averageTime);
                System.out.println("Size: " + size + ", Average Time: " + averageTime + " nanoseconds");
            }
              System.out.println("Data saved to: " + filename);
        } catch (IOException e) {
           System.err.println("Error writing to file: " + e.getMessage());
       }
   }


    public static void main(String[] args) {
       int maxSize = 10000;
        int maxValue = 1000;
         int numRuns = 20;
        generateData(maxSize, "merge_sort_data.csv", maxValue, numRuns);
    }
}
