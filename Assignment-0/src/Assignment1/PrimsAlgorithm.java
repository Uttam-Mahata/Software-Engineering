package Assignment1;
import java.io.*;
import java.util.*;

public class PrimsAlgorithm {
    static class Edge {
        int src, dest, weight;
        
        Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }
    
    static class Graph {
        int V;
        List<List<Edge>> adj;
        
        Graph(int V) {
            this.V = V;
            adj = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                adj.add(new ArrayList<>());
            }
        }
        
        void addEdge(int src, int dest, int weight) {
            Edge edge1 = new Edge(src, dest, weight);
            Edge edge2 = new Edge(dest, src, weight);
            adj.get(src).add(edge1);
            adj.get(dest).add(edge2);
        }
    }
    
    // Prim's algorithm implementation
    static int primMST(Graph graph) {
        int V = graph.V;
        int[] key = new int[V];
        boolean[] mstSet = new boolean[V];
        
        Arrays.fill(key, Integer.MAX_VALUE);
        key[0] = 0;
        
        PriorityQueue<Integer> pq = new PriorityQueue<>((v1, v2) -> key[v1] - key[v2]);
        pq.offer(0);
        
        while (!pq.isEmpty()) {
            int u = pq.poll();
            mstSet[u] = true;
            
            for (Edge edge : graph.adj.get(u)) {
                int v = edge.dest;
                int weight = edge.weight;
                
                if (!mstSet[v] && weight < key[v]) {
                    key[v] = weight;
                    pq.offer(v);
                }
            }
        }
        
        int mstWeight = 0;
        for (int i = 0; i < V; i++) {
            mstWeight += key[i];
        }
        return mstWeight;
    }
    
    // Generate random graph for testing
    static Graph generateRandomGraph(int V, double density) {
        Graph graph = new Graph(V);
        Random random = new Random();
        
        int maxEdges = (V * (V - 1)) / 2;
        int numEdges = (int) (maxEdges * density);
        
        Set<String> addedEdges = new HashSet<>();
        
        while (addedEdges.size() < numEdges) {
            int src = random.nextInt(V);
            int dest = random.nextInt(V);
            
            if (src != dest) {
                String edge = src + "," + dest;
                String reverseEdge = dest + "," + src;
                
                if (!addedEdges.contains(edge) && !addedEdges.contains(reverseEdge)) {
                    int weight = random.nextInt(100) + 1;
                    graph.addEdge(src, dest, weight);
                    addedEdges.add(edge);
                }
            }
        }
        return graph;
    }
    
    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("prims_performance.csv")) {
            writer.write("vertices,density,execution_time\n");
            
            // Test different input sizes
            int[] vertices = {10, 20, 50, 100, 200, 300, 400, 500, 1000, 1500};
            double[] densities = {0.2, 0.5, 0.8}; // Different graph densities for best, average, and worst cases
            
            for (int V : vertices) {
                for (double density : densities) {
                    Graph graph = generateRandomGraph(V, density);
                    
                    // Measure execution time
                    long startTime = System.nanoTime();
                    primMST(graph);
                    long endTime = System.nanoTime();
                    double executionTime = (endTime - startTime) / 1_000_000.0; // Convert to milliseconds
                    
                    // Write to CSV
                    writer.write(V + "," + density + "," + executionTime + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}