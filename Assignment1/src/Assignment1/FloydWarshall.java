package Assignment1;
import java.io.*;
import java.util.*;

public class FloydWarshall {
    private static final int INF = 99999;
    
    public static void floydWarshall(int[][] graph, int V) {
        int[][] dist = new int[V][V];
        
        // Initialize distance matrix
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                dist[i][j] = graph[i][j];
            }
        }
        
        // Find shortest paths
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF 
                        && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
    }
    
    // Generate random graph with given density (0.0 to 1.0)
    public static int[][] generateGraph(int V, double density) {
        Random rand = new Random();
        int[][] graph = new int[V][V];
        
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else {
                    if (rand.nextDouble() < density) {
                        graph[i][j] = rand.nextInt(100) + 1;
                    } else {
                        graph[i][j] = INF;
                    }
                }
            }
        }
        return graph;
    }
    
    public static void main(String[] args) {
        // Test sizes from 10 to 100 with step of 10
        try (FileWriter fw = new FileWriter("floyd_warshall_times.csv")) {
            fw.write("size,best_case,average_case,worst_case\n");
            
            for (int V = 10; V <= 800; V += 10) {
                // Best case: Sparse graph (low density)
                int[][] bestCase = generateGraph(V, 0.2);
                long bestStart = System.nanoTime();
                floydWarshall(bestCase, V);
                long bestTime = System.nanoTime() - bestStart;
                
                // Average case: Medium density
                int[][] avgCase = generateGraph(V, 0.5);
                long avgStart = System.nanoTime();
                floydWarshall(avgCase, V);
                long avgTime = System.nanoTime() - avgStart;
                
                // Worst case: Dense graph
                int[][] worstCase = generateGraph(V, 0.8);
                long worstStart = System.nanoTime();
                floydWarshall(worstCase, V);
                long worstTime = System.nanoTime() - worstStart;
                
                // Convert to milliseconds and write to CSV
                fw.write(String.format("%d,%.3f,%.3f,%.3f\n",
                    V,
                    bestTime / 1_000_000.0,
                    avgTime / 1_000_000.0,
                    worstTime / 1_000_000.0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}