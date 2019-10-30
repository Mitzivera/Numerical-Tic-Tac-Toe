
/**
 * Class to represent a rectangular matrix of integer values. DO NOT MODIFY
 * unless you find a bug and discuss it with Dr. Yilek first.
 * 
 * @author Scott Yilek, for use in CISC 230
 *
 */
public class IntegerMatrix {

    /** A 2d array of integers holding the values in the matrix */
    private int[][] values;
    /** The number of rows in the matrix */
    private int rows;
    /** The number of columns in the matrix */
    private int columns;

    // Standard constructor
    public IntegerMatrix(int rows, int columns) {
        if (rows < 0 || columns < 0) {
            throw new IllegalArgumentException();
        }
        this.rows = rows;
        this.columns = columns;
        this.values = new int[rows][columns];
    }

    // Copy constructor to build new matrix that is a copy of an existing matrix
    public IntegerMatrix(IntegerMatrix existingMatrix) {
        this.rows = existingMatrix.getRowCount();
        this.columns = existingMatrix.getColumnCount();
        this.values = new int[rows][columns];
        for (int row = 1; row <= this.rows; row++) {
            for (int col = 1; col <= this.columns; col++) {
                values[row - 1][col - 1] = existingMatrix.getValue(row, col);
            }
        }
    }

    /**
     * This method returns the number of rows in the matrix
     * 
     * @return number of rows
     */
    public int getRowCount() {
        return this.rows;
    }

    /**
     * This method returns the number of columns in the matrix
     * 
     * @return number of columns
     */
    public int getColumnCount() {
        return this.columns;
    }

    /**
     * This method is used to add an integer value to the matrix at a particular
     * location. The method assumes that rows and columns are 1-indexed.
     * 
     * @param row    A row number from 1 to number of rows
     * @param column A column number from 1 to number of columns
     * @param value  The value to be added to the matrix
     */
    public void setValue(int row, int column, int value) {
        if (row < 1 || row > this.rows) {
            throw new IllegalArgumentException();
        }
        if (column < 1 || column > this.columns) {
            throw new IllegalArgumentException();
        }
        values[row - 1][column - 1] = value;
    }

    /**
     * This method is used to retrieve a value from the matrix at the specified
     * position.
     * 
     * @param row    A row number (1-indexed)
     * @param column A column number (1-indexed)
     * @return The value stored at the specified position in the matrix
     */
    public int getValue(int row, int column) {
        if (row < 1 || row > this.rows) {
            throw new IllegalArgumentException();
        }
        if (column < 1 || column > this.columns) {
            throw new IllegalArgumentException();
        }
        return values[row - 1][column - 1];
    }

    /**
     * Method to get the sum of values in the specified row.
     * 
     * @param row The desired row to sum
     * @return The sum of values in the specified row
     */
    public int getRowSum(int row) {
        if (row < 1 || row > this.rows) {
            throw new IllegalArgumentException();
        }
        int sum = 0;
        for (int i = 0; i < columns; i++) {
            sum = sum + values[row - 1][i];
        }
        return sum;
    }

    /**
     * Method to get the sum of values in the specified column.
     * 
     * @param column The desired column to sum
     * @return The sum of values in the specified column
     */
    public int getColumnSum(int column) {
        if (column < 1 || column > this.columns) {
            throw new IllegalArgumentException();
        }

        int sum = 0;
        for (int i = 0; i < rows; i++) {
            sum = sum + values[i][column - 1];
        }
        return sum;
    }

    /**
     * Method to get the sum of values from the top left of the matrix to the bottom
     * right. This method should only be called on square matrices, or it will give
     * a strange result.
     * 
     * @return The sum of the values from the top left to the bottom right of the
     *         matrix
     */
    public int getDownwardDiagonalSum() {
        if (rows != columns) {
            throw new IllegalStateException();
        }
        // only works for square matrices
        int sum = 0;
        for (int i = 0; i < rows; i++) {
            sum = sum + values[i][i];
        }
        return sum;
    }

    /**
     * Method to get the sum of values from the bottom left of the matrix to the top
     * right. This method should only be called on square matrices, or it will give
     * a strange result.
     * 
     * @return The sum of the values from the bottom left to the top right of the
     *         matrix
     */
    public int getUpwardDiagonalSum() {
        if (rows != columns) {
            throw new IllegalStateException();
        }
        // only works for square matrices
        int sum = 0;
        for (int i = 0; i < rows; i++) {
            sum = sum + values[(rows - 1) - i][i];
        }
        return sum;
    }

}
