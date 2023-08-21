import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class GameBoard {
    // Game properties.
    final int boardSize = 10;
    char seaChar = '~';
    char shipChar = 'O';
    char hit = 'X';
    // multi-dimensional array to simulate game board.
    char [][] gameBoard = new char[boardSize][boardSize];

    // Initialisation of the battleships and their properties.
    ArrayList<Battleship> ships = new ArrayList<>();
    Battleship aircraftCarrier = new Battleship("Aircraft Carrier", 5);
    Battleship battleship = new Battleship("Battleship", 4);
    Battleship submarine = new Battleship("Submarine", 3);
    Battleship cruiser = new Battleship("Cruiser", 3);
    Battleship destroyer = new Battleship("Destroyer", 2);
    Scanner scanner = new Scanner(System.in);

    // Methods

    public void beginGame() {

        // Add the ships to the Arraylist.
        ships.add(aircraftCarrier);
        ships.add(battleship);
        ships.add(submarine);
        ships.add(cruiser);
        ships.add(destroyer);

        printBoard(gameBoard);

        // Loop over ship objects by getting their names and sizes from Arraylist.
        for (Battleship ship : ships) {
            System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getSize() + " cells):");
            String first = scanner.next(); // Take first co-ordinate.
            placeShips(first);
            String second = scanner.next();
            placeShips(second);
        }
        scanner.close();
    }

    public void placeShips(String coordinate) {

        char inputRow = coordinate.charAt(0); // get char for row input.
        int inputCol = Integer.parseInt(coordinate.substring(1)); // get int for col input.

        int row = Character.toUpperCase(inputRow) - 'A'; // Convert character to row index.
        int col = inputCol - 1; // Convert number to row index.

        if (coordinatesValid(row, col, boardSize)) {
            gameBoard[row][col] = shipChar;
            printBoard(gameBoard);
        } else {
            System.out.println("Invalid coordinates. Please enter valid coordinates.");
        }
    }

    public void printBoard(char[][] gameBoard) {

        char rowChar = 'A';
        int colInt = 1;

        // Loop over column ints respective of board size.
        for (int col = 0; col < boardSize; col++) System.out.print(" " + colInt++);

        System.out.println(); // Separate columns from rows.

        // Loop over row chars respective of board size.
        for (int row = 0; row < boardSize; row++) {

            System.out.print(rowChar++);

            // fill each element within the game board array with sea chars.
            for (char[] grid : gameBoard) Arrays.fill(grid, seaChar);

            // print out the entire game board once it has been filled.
            for (int columns = 0; columns < gameBoard.length; columns++) System.out.print(" " + gameBoard[row][columns]);

            System.out.println(); // Move onto next line
        }
    }

    public boolean coordinatesValid(int row, int col, int boardSize) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }
}
