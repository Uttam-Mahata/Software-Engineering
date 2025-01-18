package Assignment1;
import java.io.*;
import java.util.*;

public class KnapsackAnalysis {
    static class Item {
        int weight;
        int value;
        
        Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }
    
    // Function to solve knapsack
    public static int knapsack(Item[] items, int capacity) {
        int n = items.length;
        int[][] dp = new int[n + 1][capacity + 1];
        
        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (items[i-1].weight <= w) {
                    dp[i][w] = Math.max(
                        items[i-1].value + dp[i-1][w-items[i-1].weight],
                        dp[i-1][w]
                    );
                } else {
                    dp[i][w] = dp[i-1][w];
                }
            }
        }
        return dp[n][capacity];
    }
    
    // Generate random items for testing
    public static Item[] generateItems(int size) {
        Random rand = new Random();
        Item[] items = new Item[size];
        for (int i = 0; i < size; i++) {
            items[i] = new Item(rand.nextInt(50) + 1, rand.nextInt(100) + 1);
        }
        return items;
    }
    
    // Measure execution time for different cases
    public static void measurePerformance() {
        try (FileWriter fw = new FileWriter("knapsack_performance.csv")) {
            fw.write("size,best_case,average_case,worst_case\n");
            
            // Test different input sizes
            for (int size = 1; size <= 1000; size += 5) {
                // Best case: Small weights, small capacity
                Item[] bestCase = new Item[size];
                Arrays.fill(bestCase, new Item(1, 1));
                int bestCapacity = size/2;
                
                // Average case: Random weights and values
                Item[] avgCase = generateItems(size);
                int avgCapacity = size * 25; // moderate capacity
                
                // Worst case: Large weights, large capacity
                Item[] worstCase = new Item[size];
                Arrays.fill(worstCase, new Item(100, 100));
                int worstCapacity = size * 100;
                
                // Measure times
                long bestTime = measureTime(bestCase, bestCapacity);
                long avgTime = measureTime(avgCase, avgCapacity);
                long worstTime = measureTime(worstCase, worstCapacity);
                
                fw.write(String.format("%d,%d,%d,%d\n", 
                    size, bestTime, avgTime, worstTime));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static long measureTime(Item[] items, int capacity) {
        long startTime = System.nanoTime();
        knapsack(items, capacity);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000; // Convert to microseconds
    }
    
    public static void main(String[] args) {
        measurePerformance();
        System.out.println("Performance data has been written to knapsack_performance.csv");
    }
}