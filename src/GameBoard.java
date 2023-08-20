import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GameBoard {

    // Game properties.
    final int boardSize = 10;
    char seaChar = '~';
     char shipChar = 'O';
    char rowChar = 'A';
    int colInt = 1;

    // Initialisation of the battleships and their properties.
    ArrayList<Battleship> ships = new ArrayList<>();
    Battleship aircraftCarrier = new Battleship("Aircraft Carrier", 5);
    Battleship battleship = new Battleship("Battleship", 4);
    Battleship submarine = new Battleship("Submarine", 3);
    Battleship cruiser = new Battleship("Cruiser", 3);
    Battleship destroyer = new Battleship("Destroyer", 2);
    Scanner scanner = new Scanner(System.in);

    // multi-dimensional array to simulate game board.
    char [][] gameBoard = new char[boardSize][boardSize];

    // Methods

    public void gameInitialisation() {
        // produce the game board.
        generateGameBoard();

        // prompt player to place their battleships.
        deployPlayerBattleships();
    }

    // Method to generate the game board in the console.
    public void generateGameBoard() {

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
    // Loop over available ships and get their coordinates.
    public void deployPlayerBattleships() {
        // Add the ships to the Arraylist.
        ships.add(aircraftCarrier);
        ships.add(battleship);
        ships.add(submarine);
        ships.add(cruiser);
        ships.add(destroyer);

        // Loop over ship objects by getting their names and sizes from Arraylist.
        for (Battleship ship : ships) {
            System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getSize() + " cells):");
            String firstCoordinate = scanner.next(); // Take first co-ordinate.
            readUserInput(firstCoordinate);
            String secondCoordinate = scanner.next();
            readUserInput(secondCoordinate);
        }
    }

    // Take users co-ordinate input and place their ships on the grid.
    public void readUserInput(String coordinate) {

        try {

            char inputRow = coordinate.charAt(0); // get char for row input.
            int inputCol = Integer.parseInt(coordinate.substring(1)); // get int for col input.

            int gridRow = Character.toUpperCase(inputRow) - 'A'; // Convert character to row index.
            int gridCol = inputCol - 1; // Convert number to row index.

            if (gameBoard[inputRow][inputCol] != 'O') {

            } else {
                System.out.println("Error! Wrong ship location! Try again:");
            }

        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid co-ordinate within the range displayed.");
        } finally {
            scanner.close();
        }


    }
}
