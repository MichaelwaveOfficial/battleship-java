import java.util.Arrays;
import java.util.Scanner;

public class Game {
    // Game board properties.
    private static final int BOARD_SIZE = 10;
    // Initialise multi dimensional game board array.
    private static char[][] gameBoard = new char[BOARD_SIZE][BOARD_SIZE];
    // Chars representing status of cells on the board.
    private static final char SEA = '~', SHIP = 'O', HIT = 'X', MISS = 'M';
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
        System.out.println();
    }

    /**
     *  Prompt user to input their desired coordinates to place ship on the grid.
     */
    private static void promptUser() {
        // Initialise Ships object where ships will be added to a list.
        Ships ships = new Ships();
        // Loop over ships using lambda expression.
        ships.deployShips().forEach(battleship -> {
            // Print out ship details (name + size), then take coordinate input.
            System.out.println("Enter the coordinates of the " + battleship.getName() + " (" + battleship.getSize() + " cells):\n");
            while (true) {
                // Loop over input stage until requirements satisfied.
                try {
                    // Scanner takes first and second coordinate points as strings.
                    String firstCoordinate = scanner.next(), secondCoordinate = scanner.next();
                    // Convert string input into integer arrays storing row and column per.
                    Coordinate first = convertCoordinates(firstCoordinate), second = convertCoordinates(secondCoordinate);
                    // Check coordinates provided meet requirements.
                    if (checkCoordinates(first, second, battleship)) {
                        // Break if requirements met.
                        break;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage().contains("Error") ? "\n" + e.getMessage()
                            : "\n" + new Exception(String.format("Error! %s. Try again:" + "\n",
                            e.getLocalizedMessage())).getMessage());
                }
            }
        });
        plotGridByUserInput();
    }

    /**
     * Take coordinates, plot them on the game board, filling the empty cells in between.
     * @param first integer array representing first coordinate in computer readable format.
     * @param second integer array representing second coordinate in computer readable format.
     */
    private static void placeBattleshipOnBoard(Coordinate first, Coordinate second) {
        // Finds lowest and highest row and column values in case user enters coordinates backwards!
        int lowestRow = Math.min(first.getRow(), second.getRow()), highestRow = Math.max(first.getRow(), second.getRow());
        int lowestColumn = Math.min(first.getColumn(), second.getColumn()), highestColumn = Math.max(first.getColumn(), second.getColumn());
        // Start from first coordinate and stopping once the second one is reached.
        for (int row = lowestRow; row <= highestRow; row++) {
            for (int col = lowestColumn; col <= highestColumn; col++) gameBoard[row][col] = SHIP;
        }
        System.out.println();
        // Print the updated game board for the user to see.
        printGameBoard(gameBoard);
    }

    /**
     * Take input from user and check whether they have hit a ship or not.
     */
    private static void plotGridByUserInput() {
        // Prompt game start.
        System.out.println("The game starts!\n");
        // Print the game board to the user.
        printGameBoard(gameBoard);
        // Prompt the user to enter their input.
        System.out.println("Take a shot!\n");
        // Loop over input stage to make sure input is correct.
        while (true) {
            try {
                // Take users coordinate input as a string.
                Coordinate coordinate = convertCoordinates(scanner.next());
                // Check coordinates provided are legal.
                if (coordinatesWithinConstraints(coordinate)) {
                    if (checkForPresentShips(coordinate)) {
                        // Confirm successful hit.
                        gameBoard[coordinate.getRow()][coordinate.getColumn()] = HIT;
                        // Print game board showing hit.
                        printGameBoard(gameBoard);
                        // Inform user they landed a hit.
                        System.out.println("You hit a ship!\n");
                    } else {
                        // Otherwise, user has missed the target.
                        gameBoard[coordinate.getRow()][coordinate.getColumn()] = MISS;
                        // Print game board showing miss.
                        printGameBoard(gameBoard);
                        // Inform user they missed.
                        System.out.println("You missed!\n");
                    }
                    break;
                } else {
                    System.out.println("Error! You entered the wrong coordinates! Try again:\n");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage().contains("Error") ? "\n" + e.getMessage()
                        : "\n" + new Exception(String.format("Error! %s. Try again:" + "\n",
                        e.getLocalizedMessage())).getMessage());
            }
        }
    }

    /**
     *  Converts input from user in a readable String form into integer input the computer
     *      can understand.
     * @param coordinate Takes String coordinate input from the user.
     * @return returns Coordinate as an integer array storing the row[0] and column[1].
     */
    public static Coordinate convertCoordinates(String coordinate) {
        // Get char from String denoting the row.
        char inputRow = coordinate.charAt(0);
        // Get integer using substring method denoting column.
        int inputCol = Integer.parseInt(coordinate.substring(1));
        // Convert char into integer.
        int row = Character.toUpperCase(inputRow) - 'A';
        // Subtract 1 to index input from 0 (computer array) rather than 1(human read).
        int col = inputCol - 1;
        // Store converted variables in integer array.
        Coordinate coordinateConversion = new Coordinate(row, col);
        // Return that coordinate array to be used by another method.
        return coordinateConversion;
    }

    /**
     * Method to check coordinates are legal, meeting game constraints.
     * @param first first coordinate array.
     * @param second second coordinate array.
     * @param ship the battleship being used in time of loop.
     * @return false unless all constraints are met.
     */
    private static boolean checkCoordinates(Coordinate first, Coordinate second, Battleship ship) {
        // Get array keys
        int lowestRow = Math.min(first.getRow(), second.getRow()), highestRow = Math.max(first.getRow(), second.getRow());
        int lowestColumn = Math.min(first.getColumn(), second.getColumn()), highestColumn = Math.max(first.getColumn(), second.getColumn());
        // Get specific char from coordinate array.
        int rowLength = highestRow - lowestRow, columnLength = highestColumn - lowestColumn;
        // Check coordinates provided meet conditions.
        if (checkShipLength(rowLength, columnLength, ship)) {
            // If coordinates do not fall within the ship size constraints.
            String output = String.format("Error! Wrong length of the %s! Try again:\n", ship.getName());
            System.out.println(output);
        } else if (checkRowsAndColumnsMatch(first, second)) {
            // Either columns or rows do not match from coordinates.
            System.out.println("Error! Wrong ship location! Try again:\n");
        } else if (coordinateProximityCheck(first, second)) {
            // Ship place on or too close to preexisting ship.
            System.out.println("Error! You placed it too close to another one. Try again:\n");
        } else {
            // Place input on board if all conditions are met.
            placeBattleshipOnBoard(first, second);
            return true;
        }
        // Return false as default presuming no conditions met.
        return false;
    }

    /**
     * @param first take first coordinate given.
     * @param second take second coordinate given.
     * @return true if ship char is found present in grid location. Otherwise, return false.
     */
    private static boolean coordinateProximityCheck(Coordinate first, Coordinate second) {
        int lowestRow = Math.min(first.getRow(), second.getRow()), highestRow = Math.max(first.getRow(), second.getRow());
        int lowestColumn = Math.min(first.getColumn(), second.getColumn()), highestColumn = Math.max(first.getColumn(), second.getColumn());
        // Loop over rows starting from one cell before and after the coordinates.
        for(int row = lowestRow - 1; row <= highestRow + 1; row++) {
            // Loop over columns starting from one cell before and after the coordinates.
            for (int column = lowestColumn - 1; column <= highestColumn + 1; column++) {
                // if coordinates within constraints and char representing ship present, return true.
                if (row >= 0 && row < BOARD_SIZE && column >= 0 && column < BOARD_SIZE)
                    if (gameBoard[row][column] == SHIP) return true;
            }
        }
        return false; // Return false if no ships are present within the boundaries searched.
    }

    // Checks that distance between coordinates match ship size.
    private static boolean checkShipLength(int rowLength, int columnLength, Battleship ship) {
        return rowLength != ship.getSize() - 1 && columnLength != ship.getSize() - 1;
    }

    // Checks that coordinates are within the same rows and columns.
    private static boolean checkRowsAndColumnsMatch(Coordinate first, Coordinate second) {
        return first.getRow() != second.getRow() && first.getColumn() != second.getColumn();
    }

    // Checks that ship is present on specified place on the board.
    private static boolean checkForPresentShips(Coordinate coordinate) {
        return gameBoard[coordinate.getRow()][coordinate.getColumn()] == SHIP;
    }

    // Checks that coordinates are within the constraints of the game board.
    private static boolean coordinatesWithinConstraints(Coordinate coordinate) {
        return coordinate.getRow() >= 0 && coordinate.getRow() < BOARD_SIZE && coordinate.getColumn() >= 0 && coordinate.getColumn() < BOARD_SIZE;
    }
}

