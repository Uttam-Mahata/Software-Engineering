package Assignment1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class NearestNeighborTSP {

     static class Graph {
      int[][] adjMatrix;
      int numVertices;
      public Graph(int numVertices) {
          this.numVertices = numVertices;
           this.adjMatrix = new int[numVertices][numVertices];
      }
  }

    static int nearestNeighborTSP(Graph graph, int startNode) {
       int numVertices = graph.numVertices;
         boolean[] visited = new boolean[numVertices];
        Arrays.fill(visited, false);

        int currentCity = startNode;
         visited[startNode] = true;
        int tourCost = 0;
        int numVisited = 1;


       while (numVisited < numVertices) {
           int nearestCity = -1;
            int minDistance = Integer.MAX_VALUE;

            for (int nextCity = 0; nextCity < numVertices; nextCity++) {
                 if (!visited[nextCity] && graph.adjMatrix[currentCity][nextCity] < minDistance) {
                        minDistance = graph.adjMatrix[currentCity][nextCity];
                       nearestCity = nextCity;
                }
             }

            if (nearestCity != -1) {
                tourCost += minDistance;
                currentCity = nearestCity;
                 visited[nearestCity] = true;
                 numVisited++;
            }
       }
        tourCost += graph.adjMatrix[currentCity][startNode]; // Return to start city
        return tourCost;
    }

    static Graph createRandomGraph(int numVertices, int maxWeight) {
        Graph graph = new Graph(numVertices);
        Random random = new Random();
         for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                 if (i == j) {
                     graph.adjMatrix[i][j] = 0; // No self loops
                 } else {
                     graph.adjMatrix[i][j] = random.nextInt(maxWeight) + 1;
                 }
             }
        }
        return graph;
    }



  static void generateData(int maxVertices, String filename, int maxWeight, int numRuns) {
       try (PrintWriter outputFile = new PrintWriter(new FileWriter(filename))) {
             outputFile.println("Number of Cities,Average Time (nanoseconds)");
             for (int numVertices = 10; numVertices <= maxVertices; numVertices += 10) {
                long totalTime = 0;
                for(int run = 0; run < numRuns; run++) {
                    Graph graph = createRandomGraph(numVertices, maxWeight);
                     long startTime = System.nanoTime();
                    nearestNeighborTSP(graph, 0);
                     long endTime = System.nanoTime();
                    totalTime += (endTime - startTime);
                 }
                 long averageTime = totalTime / numRuns;
                 outputFile.println(numVertices + "," + averageTime);
                 System.out.println("Vertices: " + numVertices + ", Average Time: " + averageTime + " nanoseconds");
            }
            System.out.println("Data saved to: " + filename);
       } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
  }


    public static void main(String[] args) {
       int maxVertices = 1000; //Can increase this based on the performance of your system
        int maxWeight = 10;
        int numRuns = 20;
        generateData(maxVertices, "nearest_neighbor_tsp_data.csv", maxWeight, numRuns);
    }
}
