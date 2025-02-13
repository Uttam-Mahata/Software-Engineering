package Assignment1;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TSPAnalysis {
    private static Random random = new Random();
    
    static class TSPSolution {
        int[] path;
        double distance;
        
        TSPSolution(int[] p, double d) {
            path = p;
            distance = d;
        }
    }

    public static double[][] generateRandomCities(int n) {
        double[][] distances = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double distance = random.nextDouble() * 100;
                distances[i][j] = distance;
                distances[j][i] = distance;
            }
            distances[i][i] = 0;
        }
        return distances;
    }

    public static TSPSolution solveTSP(double[][] distances) {
        int n = distances.length;
        int[] currentPath = new int[n];
        boolean[] visited = new boolean[n];
        int[] bestPath = new int[n];
        
        // Initialize arrays
        for (int i = 0; i < n; i++) {
            currentPath[i] = -1;
            visited[i] = false;
        }
        
        currentPath[0] = 0;
        visited[0] = true;
        
        double[] bestDistance = {Double.MAX_VALUE};
        tspBacktrack(distances, currentPath, visited, 1, 0, bestDistance, bestPath);
        
        return new TSPSolution(bestPath.clone(), bestDistance[0]);
    }

    private static void tspBacktrack(double[][] distances, int[] currentPath, boolean[] visited, 
                                   int pos, double currentDistance, double[] bestDistance, int[] bestPath) {
        int n = distances.length;
        
        if (pos == n) {
            double totalDistance = currentDistance + distances[currentPath[n-1]][0];
            if (totalDistance < bestDistance[0]) {
                bestDistance[0] = totalDistance;
                System.arraycopy(currentPath, 0, bestPath, 0, n);
            }
            return;
        }
        
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                currentPath[pos] = i;
                
                double newDistance = currentDistance + distances[currentPath[pos-1]][i];
                if (newDistance < bestDistance[0]) {
                    tspBacktrack(distances, currentPath, visited, pos + 1, newDistance, bestDistance, bestPath);
                }
                
                visited[i] = false;
                currentPath[pos] = -1;
            }
        }
    }

    public static long measureTSP(int size) {
        double[][] distances = generateRandomCities(size);
        long startTime = System.nanoTime();
        solveTSP(distances);
        return System.nanoTime() - startTime;
    }

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("tsp_performance.csv")) {
            writer.write("size,execution_time\n");
            
            // Test for different numbers of cities
            for (int size = 4; size <= 12; size++) {
                long executionTime = measureTSP(size);
                writer.write(String.format("%d,%d\n", size, executionTime));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}