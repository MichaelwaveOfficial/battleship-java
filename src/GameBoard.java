import java.util.ArrayList;
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


    // Initialisation of the battleships and their properties.
    private ArrayList<Battleship> ships = new ArrayList<>();
    private Battleship aircraftCarrier = new Battleship("Aircraft Carrier", 5);
    private Battleship battleship = new Battleship("Battleship", 4);
    private Battleship submarine = new Battleship("Submarine", 3);
    private Battleship cruiser = new Battleship("Cruiser", 3);
    private Battleship destroyer = new Battleship("Destroyer", 2);

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

        // Add the ships to the Arraylist.
        ships.add(aircraftCarrier);
        ships.add(battleship);
        ships.add(submarine);
        ships.add(cruiser);
        ships.add(destroyer);


        // Loop over ship objects by getting their names and sizes from Arraylist.
        for (Battleship ship : ships) {

            System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getSize() + " cells):");

            try {
                String first = scanner.next(); // Take first co-ordinate.
                Coordinate firstCoordinate = readInput(first);

                String second = scanner.next();
                Coordinate secondCoordinate = readInput(second);

                placeShips(firstCoordinate, secondCoordinate, ship.getSize(), ship.getName());

            } catch (InputMismatchException i) {

                System.out.println("Invalid coordinates. Please enter valid coordinates.");
            }
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
     * @param shipSize make sure given coordinates match up to the ships size.
    */
    private void placeShips(Coordinate c1, Coordinate c2, int shipSize, String shipName) {

        // first coordinate.
        int firstRow = c1.getX();
        int firstCol = c1.getY();

        // second coordinate.
        int secondRow = c2.getX();
        int secondCol = c2.getY();

        // Get lengths of the coordinates placed.
        int shipsLengthRow = (secondRow - firstRow) + 1;
        int shipsLengthCol = (secondCol - firstCol) + 1;

        // Check ship is not too close to another
        //System.out.println("Error! You placed it too close to another one. Try again:");


        // Checks to see if the rows and columns match.
        if (firstRow != secondRow || firstCol != secondCol) {
            System.out.println("Error! Wrong ship location! Try again:");
        }

        // Checks ships coordinates are within the ships length.
        if (shipsLengthRow > shipSize || shipsLengthCol > shipSize) {
            // Needs to take user back to specific part of the loop.
            System.out.println("Error! Wrong length of the " + shipName + "! Try again:");
            beginGame();
        }

        // Take coordinate points and fill the gaps between them.
        if (coordinatesValid(firstRow, firstCol) && coordinatesValid(secondRow, secondCol)) {
            for (int row = firstRow; row <= secondRow; row++) {
                for (int col = firstCol; col <= secondCol; col++) gameBoard[row][col] = SHIP_CHAR;
            }
        } else {
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
    private boolean coordinatesValid(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }
}

/**
 * Introduce desired error handling.
 *      ** Co ordinates not in the same row // column.
 *      ** Ships too close // ontop of each other.
 *      ** finish over sizing.
 *      ** Just wrong input in general.
 */


