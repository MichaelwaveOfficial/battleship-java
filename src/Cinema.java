import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Cinema {


    // Variables holding grid dimensions and ticket prices.
    private static int GRID_ROWS = 0, GRID_COLUMNS = 0, FRONT_TICKET = 10, BACK_TICKET = 8, ticketsPurchased = 0, income = 0;
    // Multidimensional array holding grid representing seats in cinema.
    private static char[][] seatingArrangement = new char[GRID_ROWS][GRID_COLUMNS];
    // Chars representing status of cells on the grid.
    private static final char SEAT = 'S', BOOKED = 'B';
    // Initialise scanner for taking user input.
    private static final Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {

        try {
            // Take integer input for number of rows within cinema.
            System.out.println("Enter the number of rows:");
            GRID_ROWS = scanner.nextInt();

            // Take integer input for number of columns within cinema.
            System.out.println("Enter the number of seats in each row:");
            GRID_COLUMNS = scanner.nextInt();

            // Update grid to size specified by user.
            seatingArrangement = new char[GRID_ROWS][GRID_COLUMNS];

            // Fill grid with chars representing empty seats.
            for (char[] grid : seatingArrangement) Arrays.fill(grid, SEAT);

            // Print extra line to tidy up formatting in terminal.
            System.out.println();

            // Loop over menu to keep program constant.
            while (true) {

                // Prompt user with choices and take input using scanner to determine program functionality.
                System.out.println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n");
                int menuSelection = scanner.nextInt();

                if (menuSelection == 0) {
                    break;
                } else if (menuSelection == 1) {
                    printSeatingArrangement(seatingArrangement);
                } else if (menuSelection == 2) {
                    chooseSeats();
                } else if (menuSelection == 3) {
                    statistics();
                } else {
                    System.out.println("Please enter a valid input.");
                }

                // Another print line for formatting.
                System.out.println();
            }
        } catch (InputMismatchException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    /**
     * @param seats pass global grid variable, so it is always up-to-date for user.
     */
    private static void printSeatingArrangement(char[][] seats) {

        // Grid for users perspective will be indexed from 1.
        int row = 1;

        System.out.print("Cinema:\n ");

        // Print out column numbers and increment until GRID limit is reached.
        for (int cols = 1; cols <= GRID_COLUMNS; cols++) System.out.print(" " + cols);

        // Print line for formatting.
        System.out.println();

        // Print out entire grid.
        for (int rows = 0; rows < GRID_ROWS; rows++) {
            System.out.print(row++);
            for (int cols = 0; cols < GRID_COLUMNS; cols++) {
                System.out.print(" " + seats[rows][cols]);
            }
            // More formatting.
            System.out.println();
        }
    }

    /**
     * method to take user input and select their desired seat on the grid, also handles errors.
     */
    private static void chooseSeats() {
        // Keep looping over until correct break conditions are met.
        while (true) {
            try {
                // Take seats row number.
                System.out.println("Enter a row number:");
                int rowNumber = scanner.nextInt();

                // Take seats number within that row.
                System.out.println("Enter a seat number in that row:");
                int seatNumber = scanner.nextInt();

                // Handle input to make sure input is within the grids boundaries and that seats are not already booked.
                if (rowNumber < 0 || rowNumber > GRID_ROWS || seatNumber < 0 || seatNumber > GRID_COLUMNS) {
                    // Inform user input is incorrect.
                    System.out.println("Wrong input!");
                } else if ( seatingArrangement[rowNumber - 1][seatNumber - 1] == BOOKED) {
                    // Inform user seat is already taken.
                    System.out.println("That ticket has already been purchased!");
                } else {
                    // Passed check conditions.
                    // Ternary conditional to calculate ticket price whether seat is located towards the front or the back.
                    int price = GRID_ROWS * GRID_COLUMNS <= 60 || rowNumber <= GRID_ROWS / 2 ? FRONT_TICKET : BACK_TICKET;
                    System.out.println("Ticket price: $" + price);

                    // Set seat status to booked.
                    seatingArrangement[rowNumber - 1][seatNumber - 1] = BOOKED;
                    // Increment number of tickets purchased in global scope for later calculations.
                    ticketsPurchased++;
                    // Increment total income global variable for later calculations.
                    income += price;

                    // Break while loop and finish.
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Wrong input!");
            }
        }
        // Formatting.
        System.out.println();
    }

    /**
     * Calculate cinema statistics from ticket sales.
     */
    private static void statistics() {
        // Calculate total number of seats within the grid.
        int totalSeats = GRID_ROWS * GRID_COLUMNS;
        // Explicitly cast double type to work out percentage of the seats sold within the cinema.
        double percentageOfSeatsSold = ((double) ticketsPurchased / totalSeats) * 100;
        // Calculate total income from seats sold.
        int totalIncome = (totalSeats / 2 * FRONT_TICKET) + (totalSeats / 2 * BACK_TICKET);

        String result = String.format(
                "Number of purchased tickets: %d           \n"
                        + "Percentage: %.2f%%              \n"
                        + "Current income: $%d             \n"
                        + "Total income: $%d               \n",
                ticketsPurchased, percentageOfSeatsSold, income, totalIncome
        );
        System.out.println(result);
    }
}
