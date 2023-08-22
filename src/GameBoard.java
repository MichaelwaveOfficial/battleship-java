import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameBoard {

    // Game properties.
    private final int BOARD_SIZE = 10;
    private final char SEA_CHAR = '~';
    private final char SHIP_CHAR = 'O';
    private final char HIT = 'X';

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

            String first = scanner.next(); // Take first co-ordinate.
            Coordinate firstCoordinate = readInput(first);

            String second = scanner.next();
            Coordinate secondCoordinate = readInput(second);

            placeShips(firstCoordinate, secondCoordinate);
        }
        scanner.close();
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

        System.out.println("row : " + row + " column : " + col); // okay

        return new Coordinate(row, col); // works as intended.
    }

    /**
     *
     * @param c1
     * @param c2
     */
    public void placeShips(Coordinate c1, Coordinate c2) {

        coordinates.add(c1);
        coordinates.add(c2);

        for (Coordinate coordinate : coordinates) {

            int x = coordinate.getX();
            int y = coordinate.getY();

            if (coordinatesValid(x, y, BOARD_SIZE)) {
                gameBoard[x][y] = SHIP_CHAR;
            } else {
                System.out.println("Invalid coordinates. Please enter valid coordinates.");
            }
        }
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

        // Loop over column ints respective of board size.
        for (int col = 0; col < BOARD_SIZE; col++) System.out.print(" " + colInt++);

        // Separate columns from rows.
        System.out.println();

        // Loop over row chars respective of board size.
        for (int row = 0; row < BOARD_SIZE; row++) {

            // Increment chars for row on the gameboard.
            System.out.print(rowChar++);

            // fill each element within the game board array with sea chars.
            for (char[] grid : gameBoard) Arrays.fill(grid, SEA_CHAR);

            // print out the entire game board once it has been filled.
            for (int columns = 0; columns < gameBoard.length; columns++) System.out.print(" " + gameBoard[row][columns]);

            // Move onto next line
            System.out.println();
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


