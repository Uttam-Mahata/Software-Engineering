package Assignment1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class FractionalKnapsack {

    static class Item {
        int weight;
        int value;

        public Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }

    static double fractionalKnapsack(int capacity, Item[] items) {
        Arrays.sort(items, (a, b) -> Double.compare((double) b.value / b.weight, (double) a.value / a.weight));
        double totalValue = 0;
        int currentWeight = 0;

        for (Item item : items) {
            if (currentWeight + item.weight <= capacity) {
                totalValue += item.value;
                currentWeight += item.weight;
            } else {
                int remainingWeight = capacity - currentWeight;
                totalValue += ((double) item.value / item.weight) * remainingWeight;
                break;
            }
        }
        return totalValue;
    }



   static Item[] generateRandomItems(int numItems, int maxWeight, int maxValue) {
        Item[] items = new Item[numItems];
        Random random = new Random();
        for (int i = 0; i < numItems; i++) {
            int weight = random.nextInt(maxWeight) + 1;
            int value = random.nextInt(maxValue) + 1;
            items[i] = new Item(weight, value);
        }
        return items;
    }


  static void generateData(int maxItems, String filename, int maxCapacity, int maxWeight, int maxValue, int numRuns) {
        try (PrintWriter outputFile = new PrintWriter(new FileWriter(filename))) {
            outputFile.println("Number of Items,Average Time (nanoseconds)");

            for (int numItems = 10; numItems <= maxItems; numItems += 10) {
                long totalTime = 0;
                 for(int run = 0; run < numRuns; run++){
                  Item[] items = generateRandomItems(numItems, maxWeight, maxValue);
                     long startTime = System.nanoTime();
                    fractionalKnapsack(maxCapacity, items);
                     long endTime = System.nanoTime();
                    totalTime += (endTime - startTime);
                 }

                 long averageTime = totalTime / numRuns;
               outputFile.println(numItems + "," + averageTime);
                System.out.println("Items: " + numItems + ", Average Time: " + averageTime + " nanoseconds");
           }
          System.out.println("Data saved to: " + filename);
       } catch (IOException e) {
          System.err.println("Error writing to file: " + e.getMessage());
      }
  }


    public static void main(String[] args) {
       int maxItems = 10000;  // Reduced max items as it was very fast for very high values
        int maxCapacity = 100;
       int maxWeight = 10;
        int maxValue = 20;
         int numRuns = 20; // Increased runs for more consistent results
        generateData(maxItems, "fractional_knapsack_data.csv", maxCapacity, maxWeight, maxValue, numRuns);
    }
}
