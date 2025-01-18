package Assignment1;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NQueenAnalysis {
    private static List<long[]> solutions = new ArrayList<>();
    
    public static boolean isSafe(int[][] board, int row, int col, int N) {
        // Check this row on left side
        for (int j = 0; j < col; j++)
            if (board[row][j] == 1)
                return false;

        // Check upper diagonal on left side
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        // Check lower diagonal on left side
        for (int i = row, j = col; j >= 0 && i < N; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    public static boolean solveNQueens(int[][] board, int col, int N) {
        if (col >= N)
            return true;

        for (int i = 0; i < N; i++) {
            if (isSafe(board, i, col, N)) {
                board[i][col] = 1;

                if (solveNQueens(board, col + 1, N))
                    return true;

                board[i][col] = 0; // Backtrack
            }
        }
        return false;
    }

    public static long measureNQueen(int N) {
        int[][] board = new int[N][N];
        long startTime = System.nanoTime();
        solveNQueens(board, 0, N);
        return System.nanoTime() - startTime;
    }

    public static void main(String[] args) {
        try (FileWriter writer = new FileWriter("nqueen_performance.csv")) {
            writer.write("size,execution_time\n");
            
            // Test for different board sizes
            for (int size = 4; size <= 30; size++) {
                long executionTime = measureNQueen(size);
                writer.write(String.format("%d,%d\n", size, executionTime));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}