package Assignment1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class FloydWarshall {


    static int[][] floydWarshall(int[][] graph, int numNodes) {
        int[][] dist = new int[numNodes][numNodes];

        // Initialize distance matrix
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                dist[i][j] = graph[i][j];
            }
        }


        for (int k = 0; k < numNodes; k++) {
            for (int i = 0; i < numNodes; i++) {
                 for (int j = 0; j < numNodes; j++) {
                     if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE &&
                         dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                     }
                }
           }
        }
         return dist;
    }

    static int[][] createRandomGraph(int numNodes, double edgeProbability, int maxWeight) {
         int[][] graph = new int[numNodes][numNodes];
         Random random = new Random();
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (i == j) {
                     graph[i][j] = 0; // No self-loops
                } else if (random.nextDouble() <= edgeProbability) {
                     graph[i][j] = random.nextInt(maxWeight) + 1;
               } else {
                     graph[i][j] = Integer.MAX_VALUE; // represents no edge
                }
             }
        }
         return graph;
    }



    static void generateData(int maxNodes, String filename, double edgeProb, int maxWeight, int numRuns) {
       try (PrintWriter outputFile = new PrintWriter(new FileWriter(filename))) {
              outputFile.println("Number of Nodes,Average Time (nanoseconds)");

            for (int numNodes = 10; numNodes <= maxNodes; numNodes += 10) {
                long totalTime = 0;
                 for(int run = 0; run < numRuns; run++) {
                      int[][] graph = createRandomGraph(numNodes, edgeProb, maxWeight);
                     long startTime = System.nanoTime();
                    floydWarshall(graph, numNodes);
                     long endTime = System.nanoTime();
                     totalTime += (endTime - startTime);
                }
                 long averageTime = totalTime / numRuns;
                 outputFile.println(numNodes + "," + averageTime);
                System.out.println("Nodes: " + numNodes + ", Average Time: " + averageTime + " nanoseconds");
            }
            System.out.println("Data saved to: " + filename);
        } catch (IOException e) {
              System.err.println("Error writing to file: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        int maxNodes = 500; // Reduced maxNodes because of the cubic complexity. Adjust as needed
       double edgeProbability = 0.3;
        int maxWeight = 10;
         int numRuns = 20;
        generateData(maxNodes, "floyd_warshall_data.csv", edgeProbability, maxWeight, numRuns);
    }
}
