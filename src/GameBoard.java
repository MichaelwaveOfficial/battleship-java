import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameBoard {

    // Game properties.
    private final int BOARD_SIZE = 10;
    private final char SEA_CHAR = '~';
    private final char SHIP_CHAR = 'O';
    private final char HIT = 'X';
    private int timesInvoked;

    // multi-dimensional array to simulate game board.
    private char [][] gameBoard = new char[BOARD_SIZE][BOARD_SIZE];

    // Initialise scanner for user input.
    private Scanner scanner = new Scanner(System.in);


    // Methods

    /**
     * Method to encapsulate important game methods.
     */
    public void beginGame() {

        // Print out initial board.
        printBoard(gameBoard);

        // present the user with their battleships.
        battleshipsSetup();

    }

    /**
     * Add ships to the arraylist to be looped over.
     * Each ship taking input from user on where they should be placed on the game board.
     */
    private void battleshipsSetup() {

        // Initialise ships class to access its methods.
        Ships ships = new Ships();

        // Loop over ship objects by getting their names and sizes from Arraylist.
        for (Battleship ship : ships.deployShips()) {

            System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getSize() + " cells):");

            String first = scanner.next(); // Take first co-ordinate.
            Coordinate firstCoordinate = readInput(first);

            String second = scanner.next();
            Coordinate secondCoordinate = readInput(second);

            placeShips(firstCoordinate, secondCoordinate, ship);

        }

        scanner.close();
    }

    /**
     * @param coordinate take coordinate input as a string from user.
     * @return coordinate obj which stores X , Y as ints.
     */
    private Coordinate readInput(String coordinate) {

        char inputRow = coordinate.charAt(0); // get char for row input.
        int inputCol = Integer.parseInt(coordinate.substring(1)); // get int for col input.

        int row = Character.toUpperCase(inputRow) - 'A'; // Convert character to row index.
        int col = inputCol - 1; // Convert number to row index.

        return new Coordinate(row, col); // works as intended.
    }

    /**
     * @param c1 take first coordinate where ship will start.
     * @param c2 take second coordinate where ship will end.
     * @param ship get ships details.
    */
    private void placeShips(Coordinate c1, Coordinate c2, Battleship ship) {

        // first coordinate.
        int firstRow = c1.getX();
        int firstCol = c1.getY();

        // second coordinate.
        int secondRow = c2.getX();
        int secondCol = c2.getY();

        // Get lengths of the coordinates placed.
        int shipsLengthRow = (secondRow - firstRow) + 1;
        int shipsLengthCol = (secondCol - firstCol) + 1;

        try {

            if (coordinatesProximityCheck(firstRow, firstCol, secondRow, secondCol)) {
                System.out.println("Proximity Success");
            } else {
                System.out.println("Error! You placed it too close to another one. Try again:");
            }

            // Checks to see if the rows and columns match.
            if (firstRow == secondRow || firstCol == secondCol) {

                // Take coordinate points and fill the gaps between them.
                if (coordinatesInRange(firstRow, firstCol) && coordinatesInRange(secondRow, secondCol)) {

                    if (shipsLengthRow > ship.getSize() || shipsLengthCol > ship.getSize()) {

                        System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:");

                    } else {

                        for (int row = firstRow; row <= secondRow; row++) {
                            for (int col = firstCol; col <= secondCol; col++) gameBoard[row][col] = SHIP_CHAR;
                        }
                    }
                }
            } else {
                System.out.println("Error! Wrong ship location! Try again:");
            }

        } catch (InputMismatchException i) {
            System.out.println("Invalid coordinates. Please enter valid coordinates.");
        }

        // Reprint the updated board to the users console.
        printBoard(gameBoard);
    }

    /**
     * @param gameBoard take global game-board array so it can be updated.
     */
    private void printBoard(char[][] gameBoard) {

        // Introduce the boards rows and columns as local variables.
        char rowChar = 'A';
        int colInt = 1;

        // Format board columns.
        System.out.print(" ");

        // Loop over column ints respective of board size.
        for (int col = 0; col < BOARD_SIZE; col++) System.out.print(" " + colInt++);

        // Separate columns from rows.
        System.out.println();

        // Loop over row chars respective of board size.
        for (int row = 0; row < BOARD_SIZE; row++) {

            // Increment chars for row on the game board.
            System.out.print(rowChar++);

            // Only fill each element within the game board using sea chars if method invoked for the first time.
            if (timesInvoked < 1) for (char[] grid : gameBoard) Arrays.fill(grid, SEA_CHAR);

            // print out the entire game board once it has been filled.
            for (int columns = 0; columns < gameBoard.length; columns++) System.out.print(" " + gameBoard[row][columns]);

            // Move onto next line
            System.out.println();

            // Increment timesInvoked variable.
            timesInvoked++;
        }
    }

    /**
     * @param row make sure provided row is within the game limits.
     * @param col make sure provided column is within the game limits.
     * @return true if provided arguments are within the limits. Else, fail.
     */
    private boolean coordinatesInRange(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }

    private boolean coordinatesMatchRowsAndCols(int row, int col) {
        return row == col;
    }

    private boolean coordinatesMatchShipLength(int firstRow, int firstCol, int secRow, int secCol, Battleship ship) {
        // Get lengths of the coordinates placed.
        int shipsLengthRow = (secRow - firstRow) + 1;
        int shipsLengthCol = (secCol - firstCol) + 1;

        return shipsLengthRow <= ship.getSize() || shipsLengthCol <= ship.getSize();
    }

    private boolean coordinatesProximityCheck(int firRow, int firCol, int secRow, int secCol) {
        return firRow - secRow >= 1 && firCol - secCol >= 1;
    }

}

/**
 * Introduce desired error handling.
 *      ** Ships too close // on top of each other.
 *      retry ship rather than moving onto next.
 */


