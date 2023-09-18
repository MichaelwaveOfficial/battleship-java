import java.util.Arrays;

public class GameBoard {
    // Game board properties.
    private int BOARD_SIZE = 10;
    private char SEA = '~';
    // Variable to track number of times method has been invoked.
    private int timesInvoked = 0;

    /**
     * Prints out the game board in the users terminal.
     * @param board pass game board to be kept up to date and relayed to user.
     */
    public void printGameBoard(char[][] board) {
        // Fill grid with chars representing the empty sea. (Only required on startup!)
        if (timesInvoked < 1) for (char[] grid : board) Arrays.fill(grid, SEA);
        // Formatting of column spacing.
        System.out.print(" ");
        // Print out column numbers and increment until GRID limit is reached.
        for (int cols = 1; cols <= BOARD_SIZE; cols++) System.out.print(" " + cols);
        // Print line for formatting.
        System.out.println();
        // Declare char for identifying rows.
        char row = 'A';
        // Loop over rows and columns to print game board.
        for (int rows = 0; rows < BOARD_SIZE; rows++) {
            System.out.print(row++);
            for (int cols = 0; cols < BOARD_SIZE; cols++) {
                System.out.print(" " + board[rows][cols]);
            }
            // More formatting.
            System.out.println();
        }
        // Increment number of times method has been called.
        timesInvoked++;
        System.out.println();
    }
    // Return the size of the game board.
    public int getBoardSize() { return BOARD_SIZE; }
}
