import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameBoard {

    // Game properties.
    private final int BOARD_SIZE = 10;
    private final char SEA_CHAR = '~';
    private final char SHIP_CHAR = 'O';
    private final char HIT = 'X';
    private int timesInvoked;

    // multi-dimensional array to simulate game board.
    char [][] gameBoard = new char[BOARD_SIZE][BOARD_SIZE];

    // Initialise array list for the coordinates.
    ArrayList<Coordinate> coordinates = new ArrayList<>();

    // Initialisation of the battleships and their properties.
    ArrayList<Battleship> ships = new ArrayList<>();
    Battleship aircraftCarrier = new Battleship("Aircraft Carrier", 5);
    Battleship battleship = new Battleship("Battleship", 4);
    Battleship submarine = new Battleship("Submarine", 3);
    Battleship cruiser = new Battleship("Cruiser", 3);
    Battleship destroyer = new Battleship("Destroyer", 2);

    // Initialise scanner for user input.
    Scanner scanner = new Scanner(System.in);

    // Methods

    /**
     *
     */
    public void beginGame() {
        // Add the ships to the Arraylist.
        ships.add(aircraftCarrier);
        ships.add(battleship);
        ships.add(submarine);
        ships.add(cruiser);
        ships.add(destroyer);

        // Print out initial board.
        printBoard(gameBoard);

        // Loop over ship objects by getting their names and sizes from Arraylist.
        for (Battleship ship : ships) {

            System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getSize() + " cells):");

            String first = scanner.next(); // Take first co-ordinate.
            Coordinate firstCoordinate = readInput(first);

            String second = scanner.next();
            Coordinate secondCoordinate = readInput(second);

            placeShips(firstCoordinate, secondCoordinate);
        }
        scanner.close(); // Rejig this, just separate co ords here? into ints, pass into placeShips func.
    }

    /**
     *
     * @param coordinate
     * @return coordinate obj
     */
    public Coordinate readInput(String coordinate) {

        char inputRow = coordinate.charAt(0); // get char for row input.
        int inputCol = Integer.parseInt(coordinate.substring(1)); // get int for col input.

        int row = Character.toUpperCase(inputRow) - 'A'; // Convert character to row index.
        int col = inputCol - 1; // Convert number to row index.

        return new Coordinate(row, col); // works as intended.
    }

    /**
     *
     * @param c1
     * @param c2
     */
    public void placeShips(Coordinate c1, Coordinate c2) {

        // Think this is the causative agent of all issues.
        // Get start and end of columns and rows.
        int startRow = Math.min(c1.getX(), c2.getX());
        int endRow = Math.max(c1.getY(), c2.getY());

        int startCol = Math.min(c1.getX(), c2.getX());
        int endCol = Math.max(c1.getY(), c2.getY());

        /**
         * If coordinates are valid, loop over board array and fill
         *      specified grid areas placing the ships.
         */
        if (coordinatesValid(startRow, startCol, BOARD_SIZE) && coordinatesValid(endRow, endCol, BOARD_SIZE)) {
            for (int row = startRow; row <= endRow; row++) {
                for (int col = startCol; col <= endCol; col++) gameBoard[row][col] = SHIP_CHAR;
            }
        } else {
            System.out.println("Invalid coordinates. Please enter valid coordinates.");
        }

        // Reprint the updated game board.
        printBoard(gameBoard);
    }

    /**
     *
     * @param gameBoard
     */
    public void printBoard(char[][] gameBoard) {

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
     *
     * @param row
     * @param col
     * @param boardSize
     * @return
     */
    public boolean coordinatesValid(int row, int col, int boardSize) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }
}

/**
 * Think print board method is filling with sea chars every time its called, rendering changes useless.
 */

