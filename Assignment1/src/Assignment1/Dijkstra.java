package Assignment1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Dijkstra {

    static class Edge {
        int to;
        int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static class Node implements Comparable<Node> {
        int id;
        int distance;

        public Node(int id, int distance) {
            this.id = id;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.distance, other.distance);
        }
    }

    static void dijkstra(int start, List<List<Edge>> graph, int[] dist) {
        int n = graph.size();
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.id;

            if (current.distance > dist[u]) {
                continue; // optimization
            }
            for (Edge edge : graph.get(u)) {
                int v = edge.to;
                int weight = edge.weight;
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pq.offer(new Node(v, dist[v]));
                }
            }
        }
    }

    static List<List<Edge>> createRandomGraph(int numNodes, double edgeProbability, int maxWeight) {
        List<List<Edge>> graph = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numNodes; i++) {
            graph.add(new ArrayList<>());
            for(int j = 0; j < numNodes; j++){
                if (i != j && random.nextDouble() <= edgeProbability) {
                    int weight = random.nextInt(maxWeight) + 1;
                    graph.get(i).add(new Edge(j, weight));
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
                for (int run = 0; run < numRuns; run++) {
                    List<List<Edge>> graph = createRandomGraph(numNodes, edgeProb, maxWeight);
                    int[] dist = new int[numNodes];
                    long startTime = System.nanoTime();
                    dijkstra(0, graph, dist);
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
        double edgeProbability = 0.3;
        int maxWeight = 10;
        int numRuns = 20;   // Number of times to run the alg for same node size and then do an average
        generateData(maxNodes, "dijkstra_data.csv", edgeProbability, maxWeight, numRuns);
    }
}