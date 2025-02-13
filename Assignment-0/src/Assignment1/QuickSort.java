package Assignment1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class QuickSort {

    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
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
               for(int run = 0; run < numRuns; run++){
                     int[] arr = generateRandomArray(size, maxValue);
                     long startTime = System.nanoTime();
                     quickSort(arr, 0, size - 1);
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
        int maxSize = 1000;
        int maxValue = 1000;
        int numRuns = 20;
        generateData(maxSize, "quick_sort_data.csv", maxValue, numRuns);
    }
}
