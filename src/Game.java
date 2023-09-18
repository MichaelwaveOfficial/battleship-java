import java.util.Scanner;

public class Game {
    // Initialise scanner for taking user input.
    private static final Scanner scanner = new Scanner(System.in);
    private static final Coordinate coordinate = new Coordinate();
    private static final GameBoard board = new GameBoard();
    // Chars representing status of cells on the board.
    private static final char SHIP = 'O', HIT = 'X', MISS = 'M';
    // Initialise multi dimensional game board array.
    private static char[][] gameBoard = new char[board.getBoardSize()][board.getBoardSize()];

    /**
     * Main method to get game running.
     * @param args nuf said.
     */
    public static void main(String[] args) {
        // Print the game board.
        board.printGameBoard(gameBoard);
        // Prompt user, starting the game.
        promptUser();
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
                    Coordinate first = coordinate.convertCoordinates(firstCoordinate), second = coordinate.convertCoordinates(secondCoordinate);
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
        // Start from first coordinate and stopping once the second one is reached.
        for (int row = coordinate.lowestCoordinate(first, second).getRow(); row <= coordinate.highestCoordinate(first, second).getRow(); row++) {
            for (int col = coordinate.lowestCoordinate(first, second).getColumn(); col <= coordinate.highestCoordinate(first, second).getColumn(); col++) gameBoard[row][col] = SHIP;
        }
        System.out.println();
        // Print the updated game board for the user to see.
        board.printGameBoard(gameBoard);
    }

    /**
     *
     */
    private static void plotGridByUserInput() {
        System.out.println("The game starts!\n");
        board.printGameBoard(gameBoard);
        System.out.println("Take a shot!\n");
        while (true) {
            // Take users coordinate input as a string.
            Coordinate userInput = coordinate.convertCoordinates(scanner.next());
            // Check logic.
            if (coordinatesWithinConstraints(userInput)) {
                if (checkForPresentShips(userInput)) {
                    // Confirm successful hit.
                    gameBoard[userInput.getRow()][userInput.getColumn()] = HIT;
                    board.printGameBoard(gameBoard);
                    System.out.println("You hit a ship!\n");
                } else {
                    // Otherwise, user has missed the target.
                    gameBoard[userInput.getRow()][userInput.getColumn()] = MISS;
                    board.printGameBoard(gameBoard);
                    System.out.println("You missed!\n");
                }
                break;
            } else {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            }
        }
    }

    /**
     * Method to check coordinates are legal, meeting game constraints.
     *
     * @param first first coordinate array.
     * @param second second coordinate array.
     * @param ship the battleship being used in time of loop.
     * @return false unless all constraints are met.
     */
    private static boolean checkCoordinates(Coordinate first, Coordinate second, Battleship ship) {
        // Get specific char from coordinate array.
        int rowLength = coordinate.highestCoordinate(first, second).getRow() - coordinate.lowestCoordinate(first, second).getRow(),
                columnLength = coordinate.highestCoordinate(first, second).getColumn() - coordinate.lowestCoordinate(first, second).getColumn();
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
        // Loop over rows starting from one cell before and after the coordinates.
        for(int row = coordinate.lowestCoordinate(first,second).getRow() - 1; row <= coordinate.highestCoordinate(first,second).getRow() + 1; row++) {
            // Loop over columns starting from one cell before and after the coordinates.
            for (int column = coordinate.lowestCoordinate(first,second).getColumn() - 1; column <= coordinate.highestCoordinate(first,second).getColumn() + 1; column++) {
                // if coordinates within constraints and char representing ship present, return true.
                if (row >= 0 && row < board.getBoardSize() && column >= 0 && column < board.getBoardSize())
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
        return coordinate.getRow() >= 0 && coordinate.getRow() < board.getBoardSize() && coordinate.getColumn() >= 0 && coordinate.getColumn() < board.getBoardSize();
    }
}
