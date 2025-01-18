package Assignment1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Kruskal {

    static class Edge implements Comparable<Edge> {
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    static class DisjointSet {
        int[] parent;
        int[] rank;

        public DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        int find(int i) {
            if (parent[i] == i) {
                return i;
            }
            return parent[i] = find(parent[i]); // Path compression
        }

        void union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);
            if (rootI != rootJ) {
                if (rank[rootI] < rank[rootJ]) {
                    parent[rootI] = rootJ;
                } else if (rank[rootI] > rank[rootJ]) {
                    parent[rootJ] = rootI;
                } else {
                    parent[rootJ] = rootI;
                    rank[rootI]++;
                }
            }
        }
    }

    static int kruskal(List<Edge> edges, int numNodes) {
        Collections.sort(edges); // Sort edges by weight
        DisjointSet ds = new DisjointSet(numNodes);
        int mstWeight = 0;
        for (Edge edge : edges) {
            if (ds.find(edge.from) != ds.find(edge.to)) {
                ds.union(edge.from, edge.to);
                mstWeight += edge.weight;
            }
        }
        return mstWeight;
    }

    static List<Edge> createRandomGraph(int numNodes, double edgeProbability, int maxWeight) {
        List<Edge> edges = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numNodes; i++) {
            for (int j = i + 1; j < numNodes; j++) {
                if (random.nextDouble() <= edgeProbability) {
                    int weight = random.nextInt(maxWeight) + 1;
                    edges.add(new Edge(i, j, weight));
                }
            }
        }
        return edges;
    }

    static void generateData(int maxNodes, String filename, double edgeProb, int maxWeight, int numRuns) {
        try (PrintWriter outputFile = new PrintWriter(new FileWriter(filename))) {
            outputFile.println("Number of Nodes,Average Time (nanoseconds)");
            for (int numNodes = 10; numNodes <= maxNodes; numNodes += 10) {
                long totalTime = 0;
                for (int run = 0; run < numRuns; run++) {
                    List<Edge> edges = createRandomGraph(numNodes, edgeProb, maxWeight);
                    long startTime = System.nanoTime();
                    kruskal(edges, numNodes);
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
        int maxNodes = 1500;
        double edgeProbability = 0.6;
        int maxWeight = 30;
        int numRuns = 10;
        generateData(maxNodes, "kruskal_data.csv", edgeProbability, maxWeight, numRuns);
    }
}