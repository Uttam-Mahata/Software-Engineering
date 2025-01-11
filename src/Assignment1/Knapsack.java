package Assignment1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Knapsack {

    static class Item {
        int weight;
        int value;

        public Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }

    static int knapsack(int capacity, Item[] items) {
        int n = items.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                if (items[i - 1].weight <= w) {
                    dp[i][w] = Math.max(items[i - 1].value + dp[i - 1][w - items[i - 1].weight], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }
        return dp[n][capacity];
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
                for(int run = 0; run < numRuns; run++) {
                    Item[] items = generateRandomItems(numItems, maxWeight, maxValue);
                    long startTime = System.nanoTime();
                    knapsack(maxCapacity, items);
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
        int maxItems = 500;
        int maxCapacity = 100; // You can also vary this to see trends
        int maxWeight = 10;
        int maxValue = 20;
        int numRuns = 20; // for better results
        generateData(maxItems, "knapsack_data.csv", maxCapacity, maxWeight, maxValue, numRuns);
    }
}
