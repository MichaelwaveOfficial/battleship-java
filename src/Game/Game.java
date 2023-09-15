package Game;

import java.util.Arrays;
import java.util.Scanner;

public class Game {
    // Game board properties.
    private static final int BOARD_SIZE = 10;
    // Initialise multi dimensional game board array.
    private static char[][] gameBoard = new char[BOARD_SIZE][BOARD_SIZE];
    // Chars representing status of cells on the board.
    private static final char SEA = '~', SHIP = 'O';
    // Variable to track number of times method has been invoked.
    private static int timesInvoked = 0;
    // Initialise scanner for taking user input.
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Main method to get game running.
     * @param args
     */
    public static void main(String[] args) {
        // Initialise game board.
        gameBoard = new char[BOARD_SIZE][BOARD_SIZE];
        // Print the game board.
        printGameBoard(gameBoard);
        // Prompt user, starting the game.
        promptUser();
    }

    /**
     * Prints out the game board in the users terminal.
     *
     * @param board pass game board to be kept up to date and relayed to user.
     */
    private static void printGameBoard(char[][] board) {
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
    }

    /**
     *  Prompt user to input their desired coordinates to place ship on the grid.
     */
    private static void promptUser() {
        // Initialise Ships object where ships will be added to a list.
        ShipList ships = new ShipList();
        // Loop over ships using lambda expression.
        ships.deployShips().forEach(battleship -> {
            // Print out ship details (name + size), then take coordinate input.
            System.out.println("Enter the coordinates of the " + battleship.getName() + " (" + battleship.getSize() + " cells):");
            while (true) {
                String firstCoordinate = scanner.next(), secondCoordinate = scanner.next();
                // Convert string input into integer arrays storing row and column per.
                int[] first = convertCoordinates(firstCoordinate), second = convertCoordinates(secondCoordinate);
                // Check coordinates provided meet requirements.
                if (checkCoordinates(first, second, battleship)) {
                    // Break if requirements met.
                    break;
                }
            }
        });
    }

    /**
     *  Converts input from user in a readable String form into integer input the computer
     *      can understand.
     * @param coordinate Takes String coordinate input from the user.
     * @return returns Coordinate as an integer array storing the row[0] and column[1].
     */
    public static int[] convertCoordinates(String coordinate) {
        // Get char from String denoting the row.
        char inputRow = coordinate.charAt(0);
        // Get integer using substring method denoting column.
        int inputCol = Integer.parseInt(coordinate.substring(1));
        // Convert char into integer.
        int row = Character.toUpperCase(inputRow) - 'A';
        // Subtract 1 to index input from 0 (computer array) rather than 1(human read).
        int col = inputCol - 1;
        // Store converted variables in integer array.
        int[] coordinateConversion = {row, col};
        // Return that coordinate array to be used by another method.
        return coordinateConversion;
    }

    /**
     * Take coordinates, plot them on the game board, filling the empty cells in between.
     *
     * @param first integer array representing first coordinate in computer readable format.
     * @param second integer array representing second coordinate in computer readable format.
     */
    private static void placeBattleshipOnBoard(int[] first, int[] second) {
        // Start from first coordinate and stopping once the second one is reached.
        for (int row = first[0]; row <= second[0]; row++) {
            for (int col = first[1]; col <= second[1]; col++) gameBoard[row][col] = SHIP;
        }
        // Print the updated game board for the user to see.
        printGameBoard(gameBoard);
    }

    /**
     * Method to check coordinates are legal, meeting game constraints.
     *
     * @param first first coordinate array.
     * @param second second coordinate array.
     * @param ship the battleship being used in time of loop.
     * @return false unless all constraints are met.
     */
    private static boolean checkCoordinates(int[] first, int[] second, Battleship ship) {
        // Get specific char from coordinate array.
        int rowLength = second[0] - first[0], columnLength = second[1] - first[1];
        // Check coordinates provided meet conditions.
        if (rowLength > ship.getSize() || columnLength > ship.getSize()) {
            // If coordinates do not fall within the ship size constraints.
            String output = String.format("Error! Wrong length of the %s! Try again:", ship.getName());
            System.out.println(output);
        } else if (first[0] != second[0] && first[1] != second[1]) {
            // Either columns or rows do not match from coordinates.
            System.out.println("Error! Wrong ship location! Try again:");
        } else if (coordinateProximityCheck(first) && coordinateProximityCheck(second)) {
            // Ship place on or too close to preexisting ship.
            System.out.println("Error! You placed it too close to another one. Try again:");
        } else {
            // Place input on board if all conditions are met.
            placeBattleshipOnBoard(first, second);
            return true;
        }
        // Return false as default presuming no conditions met.
        return false;
    }

    /**
     *
     * @param coordinate takes coordinate array.
     * @return false(no ship present or too close to pre-existing).
     */
    private static boolean coordinateProximityCheck(int[] coordinate) {
        // Loop over rows starting from one cell before and after the coordinates.
        for(int row = coordinate[0] - 1; row <= coordinate[0] + 1; row++) {
            // Loop over columns starting from one cell before and after the coordinates.
            for (int column = coordinate[1] - 1; column <= coordinate[1] + 1; column++) {
                // if coordinates within constraints and char representing ship present, return true.
                if (row >= 0 && row < BOARD_SIZE && column >= 0 && column < BOARD_SIZE)
                    if (gameBoard[row][column] == SHIP) return true;
            }
        }
        // Return false if no ships are present within the boundaries searched.
        return false;
    }
}
