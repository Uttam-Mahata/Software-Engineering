package Assignment1;

import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class Kruskal {

    static class Edge implements Comparable<Edge> {
        int src, dest, weight;

        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    static class Graph {
        int V, E;
        Edge[] edges;

        public Graph(int V, int E) {
            this.V = V;
            this.E = E;
            edges = new Edge[E];
        }
    }

    static class Subset {
        int parent, rank;
    }

    static int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent); // Path compression
        }
        return subsets[i].parent;
    }

    static void union(Subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank) {
            subsets[xroot].parent = yroot;
        } else if (subsets[xroot].rank > subsets[yroot].rank) {
            subsets[yroot].parent = xroot;
        } else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    public static List<Edge> kruskalMST(Graph graph) {
        int V = graph.V;
        Edge[] edges = graph.edges;
        List<Edge> result = new ArrayList<>();

        Arrays.sort(edges); // Sort edges by weight

        Subset[] subsets = new Subset[V];
        for (int v = 0; v < V; v++) {
            subsets[v] = new Subset();
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        int e = 0;
        int i = 0;
        while (e < V - 1 && i < graph.E) {
            Edge nextEdge = edges[i++];

            int x = find(subsets, nextEdge.src);
            int y = find(subsets, nextEdge.dest);

            if (x != y) {
                result.add(nextEdge);
                union(subsets, x, y);
                e++;
            }
        }

        return result;
    }


    public static void main(String[] args) {
        int[] numVertices = {10, 50, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
        String csvFile = "kruskal_timing.csv";

        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append("Vertices,Edges,RunTime(ns)\n");

            for(int v : numVertices){
                // Ensure edges are sufficient to create a connected graph
                int numEdges = v * 2;

                Graph graph = generateRandomGraph(v,numEdges);
                long startTime = System.nanoTime();
                kruskalMST(graph);
                long endTime = System.nanoTime();
                long runTime = endTime - startTime;

                writer.append(String.format("%d,%d,%d\n",v,numEdges,runTime));

                System.out.println("Vertices: " + v + ", Edges: " + numEdges + ", Run Time: " + runTime + " ns");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Graph generateRandomGraph(int vertices, int edges) {
        Graph graph = new Graph(vertices, edges);
        Random random = new Random();

        // Ensure graph is connected
        for(int i = 0; i < vertices - 1; i++){
            graph.edges[i] = new Edge(i, i + 1, random.nextInt(100) + 1); // Add edge of random weight
        }

        //Add additional edges randomly
        int edgeIndex = vertices - 1;
        while(edgeIndex < edges){
            int src = random.nextInt(vertices);
            int dest = random.nextInt(vertices);
            if (src != dest) { //avoid self loops
                graph.edges[edgeIndex++] = new Edge(src, dest, random.nextInt(100) + 1);
            }
        }

        return graph;
    }

}
