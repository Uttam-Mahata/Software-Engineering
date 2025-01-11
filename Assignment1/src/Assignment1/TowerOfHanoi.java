package Assignment1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TowerOfHanoi {

    // Tower of Hanoi Implementation
    static void towerOfHanoi(int n, char src, char dest, char aux) {
        if (n == 1) {
            // System.out.println("Move disk 1 from " + src + " to " + dest); // Commented out to avoid massive output for larger n
            return;
        }
        towerOfHanoi(n - 1, src, aux, dest);
        // System.out.println("Move disk " + n + " from " + src + " to " + dest); // Commented out to avoid massive output for larger n
        towerOfHanoi(n - 1, aux, dest, src);
    }

    static void generateData(int maxN, String filename) {
        try (PrintWriter outputFile = new PrintWriter(new FileWriter(filename))) {
            outputFile.println("Input Size,Time (nanoseconds)"); // CSV header

            for (int i = 1; i <= maxN; ++i) {
                int n = i; // Input size will increase exponentially
                long start = System.nanoTime();
                towerOfHanoi(n, 'A', 'C', 'B');
                long end = System.nanoTime();
                long timeTaken = end - start;
                outputFile.println(n + "," + timeTaken);
                System.out.println("Input Size: " + n + ", Time: " + timeTaken + " nanoseconds");
            }
            System.out.println("Data saved to: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Enter the number of disks: ");
        int maxN = scanner.nextInt();
        scanner.close();

        generateData(maxN, "tower_of_hanoi_data.csv");
    }
}