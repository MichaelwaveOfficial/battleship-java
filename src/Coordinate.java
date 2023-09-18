public class Coordinate {
    // Private coordinate instance variables.
    private int row, column;
    // Constructor to store row and column data in coordinate object.
    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }
    // Overridden constructor to take no args, allows to access class methods.
    public Coordinate() {}
    // Getters to access the instance variables for the coordinate objects.
    public int getRow() { return row; }
    public int getColumn() { return column; }

    /**
     *  Converts input from user in a readable String form into integer input the computer
     *      can understand.
     * @param coordinate Takes String coordinate input from the user.
     * @return returns Coordinate as an integer array storing the row[0] and column[1].
     */
    public Coordinate convertCoordinates(String coordinate) {
        // Get char from String denoting the row.
        char inputRow = coordinate.charAt(0);
        // Get integer using substring method denoting column.
        int inputCol = Integer.parseInt(coordinate.substring(1));
        // Convert char into integer.
        int row = Character.toUpperCase(inputRow) - 'A';
        // Subtract 1 to index input from 0 (computer array) rather than 1(human read).
        int col = inputCol - 1;
        // Store converted variables in integer array.
        // Return that coordinate array to be used by another method.
        return new Coordinate(row, col);
    }

    /**
     * Finds lowest row and column values.
     * @param first coordinate object.
     * @param second coordinate object.
     * @return lowest after comparing the two objects.
     */
    public Coordinate lowestCoordinate(Coordinate first, Coordinate second) {
        int lowestRow = Math.min(first.getRow(), second.getRow()), lowestColumn = Math.min(first.getColumn(), second.getColumn());
        return new Coordinate(lowestRow, lowestColumn);
    }

    /**
     * Finds highest row and column values.
     * @param first coordinate object.
     * @param second coordinate object.
     * @return highest after comparing the two objects.
     */
    public Coordinate highestCoordinate(Coordinate first, Coordinate second) {
        int highestRow = Math.max(first.getRow(), second.getRow()), highestColumn = Math.max(first.getColumn(), second.getColumn());
        return new Coordinate(highestRow, highestColumn);
    }
}
