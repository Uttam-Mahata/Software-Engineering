package Assignment1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Prim {

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

    static int prim(List<List<Edge>> graph, int numNodes) {
        int mstWeight = 0;
        boolean[] visited = new boolean[numNodes];
        int[] dist = new int[numNodes];
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[0] = 0; // start at node 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(0, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.id;
            if(visited[u]) continue; //optimization to avoid unnecessary processing of node if already included
            visited[u] = true;
            mstWeight += current.distance;

            for (Edge edge : graph.get(u)) {
                int v = edge.to;
                int weight = edge.weight;
                if (!visited[v] && weight < dist[v]) {
                    dist[v] = weight;
                    pq.offer(new Node(v, weight));
                }
            }
        }
        return mstWeight;
    }



    static List<List<Edge>> createRandomGraph(int numNodes, double edgeProbability, int maxWeight) {
        List<List<Edge>> graph = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numNodes; i++) {
            graph.add(new ArrayList<>());
            for (int j = 0; j < numNodes; j++) {
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
                    long startTime = System.nanoTime();
                    prim(graph, numNodes);
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
        int maxNodes = 500;
        double edgeProbability = 0.3;
        int maxWeight = 10;
        int numRuns = 20;
        generateData(maxNodes, "prim_data.csv", edgeProbability, maxWeight, numRuns);
    }
}