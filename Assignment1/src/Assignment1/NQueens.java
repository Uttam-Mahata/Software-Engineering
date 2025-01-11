package Assignment1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class NQueens {

   static boolean isSafe(int[][] board, int row, int col, int n) {
       int i, j;

        // Check this row on left side
        for (i = 0; i < col; i++) {
            if (board[row][i] == 1)
                return false;
        }

        // Check upper diagonal on left side
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
             if (board[i][j] == 1)
                return false;
        }

       // Check lower diagonal on left side
       for (i = row, j = col; j >= 0 && i < n; i++, j--) {
          if (board[i][j] == 1)
                return false;
      }

        return true;
    }



    static boolean solveNQueensUtil(int[][] board, int col, int n) {
        if (col >= n)
            return true;

        for (int i = 0; i < n; i++) {
            if (isSafe(board, i, col, n)) {
                board[i][col] = 1;

                if (solveNQueensUtil(board, col + 1, n))
                    return true;

                board[i][col] = 0; // Backtrack
           }
        }
        return false;
   }


    static int solveNQueens(int n) {
        int[][] board = new int[n][n];
       // Initialize the board with 0
        for (int i = 0; i < n; i++) {
            Arrays.fill(board[i], 0);
        }
        if (solveNQueensUtil(board, 0, n)) {
          return 1;
        }
       return 0;
    }

  static void generateData(int maxN, String filename, int numRuns) {
    try (PrintWriter outputFile = new PrintWriter(new FileWriter(filename))) {
         outputFile.println("Input Size,Average Time (nanoseconds)");

        for (int n = 4; n <= maxN; n++) { // Start at 4, as there is no valid solution for 1,2,3.
             long totalTime = 0;
             for(int run = 0; run < numRuns; run++){
                  long startTime = System.nanoTime();
                 solveNQueens(n);
                 long endTime = System.nanoTime();
                 totalTime += (endTime - startTime);
               }
               long averageTime = totalTime / numRuns;
             outputFile.println(n + "," + averageTime);
             System.out.println("N: " + n + ", Average Time: " + averageTime + " nanoseconds");
            }
       System.out.println("Data saved to: " + filename);
   } catch (IOException e) {
         System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
       int maxN = 16; // Reduced max value to avoid long execution time. Can be increased with caution
        int numRuns = 20;
        generateData(maxN, "nqueens_data.csv", numRuns);
    }
}