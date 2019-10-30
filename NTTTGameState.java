import java.util.ArrayList;

/**
 * Class to represent the current state of a NTTT game, including the moves available to each player 
 * and the state of the 3x3 board.  Note: no error checking currently, but none should be needed for this lab.
 * DO NOT MODIFY unless you find a bug and discuss it with Dr. Yilek first.
 * 
 * @author Scott Yilek, for use in CISC 230
 *
 */
public class NTTTGameState {

    // class variables, constants
    /** The dimensions of the board.  Set to 3 so a 3x3 board. */
    public static final int BOARD_SIZE = 3;
    /** The target value three in a row must sum to for a win */
    public static final int TARGET = 15;
    
    // instance variables
    /** Matrix to represent the board.  0 in the matrix will be an un-played spot. */
    private IntegerMatrix board;
    /** List of available integers for the odd player */
    private ArrayList<Integer> oddAvailable;
    /** List of available integers for the even player */
    private ArrayList<Integer> evenAvailable;
    /** Boolean variable saying whether the current game is over or not */
    private boolean isOver;
    /** If the game is over and one side wins, this variable will hold the winner.  Otherwise, it will be null */
    private PlayerSide winner;
    /** Which move we are currently on.  Will start at 1 and be incremented after each move. */
    private int moveCount;
    
    
    // constructors
    
    //standard constructor
    public NTTTGameState() {
        this.isOver = false;
        this.winner = null;
        this.moveCount = 1;
        this.board = new IntegerMatrix(BOARD_SIZE, BOARD_SIZE); 
        oddAvailable = new ArrayList<Integer>();
        for(int i = 1; i <=BOARD_SIZE*BOARD_SIZE; i = i+2) {
            oddAvailable.add(i);
        }
        //odd should be 1,3,5,7,9
        
        evenAvailable = new ArrayList<Integer>();
        for(int i = 2; i <=BOARD_SIZE*BOARD_SIZE; i = i+2) {
            evenAvailable.add(i);
        }
        //even should be 2,4,6,8
        
    }
    
    // copy constructor
    public NTTTGameState(NTTTGameState existingState) {
        this.isOver = existingState.isOver;
        this.winner = existingState.winner;
        this.moveCount = existingState.moveCount;
        this.board = new IntegerMatrix(existingState.board); // call IntegerMatrix's copy constructor
        oddAvailable = new ArrayList<Integer>(existingState.oddAvailable);
        evenAvailable = new ArrayList<Integer>(existingState.evenAvailable);
        
        
    }
    
    /**
     * Method that returns whose turn it currently is
     * @return The player whose turn it is
     */
    public PlayerSide whoseTurn() {
        if (this.moveCount%2 == 1) {
            return PlayerSide.ODD;
        } else {
            return PlayerSide.EVEN;
        }
    }
    
    /**
     * Method to find out who won the game, if it is complete
     * @return Either the winner or null if there is no winner
     */
    public PlayerSide getWinner() {
        return this.winner;
    }
    
    /**
     * Method to find the row a given position will be in
     * @param position A board position 1-9
     * @return Which row of the board (1-3) the position corresponds to
     */
    private int positionToRow(int position) {
        return (position-1)/BOARD_SIZE+1;
    }
    
    /**
     * Method to find the column a given position will be in
     * @param position A board position 1-9
     * @return Which column of the board (1-3) the position corresponds to
     */
    private int positionToColumn(int position) {
        return (position-1)%BOARD_SIZE+1;
    }
    
    /**
     * Method to determine if a given position (1-9) is available for a move
     * @param position A board position 1-9
     * @return true if the position is open, false otherwise
     */
    public boolean isAvailable(int position) {
        
        return isAvailable(positionToRow(position), positionToColumn(position));
    }
    
    /** 
     * Method to determine if a given position (row, column) is available for a move
     * @param row The row 1-3
     * @param column The column 1-3
     * @return true if the position is open, false otherwise
     */
    public boolean isAvailable(int row, int column) {
        return board.getValue(row, column)==0;
    }
    
    /**
     * Method to get the available values for the given player
     * @param side A PlayerSide, either ODD or EVEN
     * @return An ArrayList of integers giving the available values
     */
    public ArrayList<Integer> getAvailableValues(PlayerSide side) {
        if (side==PlayerSide.EVEN) {
            return evenAvailable;
        } else if (side==PlayerSide.ODD) {
            return oddAvailable;
        } else {
            return null;
        }
    }
    
    /**
     * Method to get the available/open positions on the board
     * @return An ArrayList of integers (1-9) corresponding to open positions
     */
    public ArrayList<Integer> getAvailablePositions() {
        ArrayList<Integer> positions = new ArrayList<Integer>();
        for(int i = 1; i <= 9; i++) {
            if (isAvailable(i)) {
                positions.add(i);
            }
        }
        return positions;
        
    }
    
    /**
     * This method is called by Players to make a move and have the board updated.  After 
     * the move is made, the method will also check if the game is over and update instance 
     * variables appropriately
     *  
     * @param position The position to make a move at
     * @param value The value to place in the given position
     */
    public void makeMove(int position, int value) {
        //position 1-9
        // need to still check that turn number is odd/even like value
        
        int row = positionToRow(position);
        int column = positionToColumn(position);
        
        
        // need to check value available
        if (!isOver && isAvailable(position)) {
            
            if (value %2 == 0) {
                board.setValue(row, column, value);
                evenAvailable.remove(new Integer(value));
            } else {
                //odd
                board.setValue(row, column, value);
                oddAvailable.remove(new Integer(value));
            }
            
            if (checkForWin()) {
                if (moveCount%2==0) {
                    winner = PlayerSide.EVEN;
                } else {
                    winner = PlayerSide.ODD;
                }
                isOver=true;
            } else if (checkForDraw()) {
                isOver=true;
            }
            
            moveCount = moveCount+1;
            
        }
        
        
    }
    
    /**
     * Method to determine if the game is over (either a win/loss or draw)
     * @return true if the game is completed, false if the game can continue with more moves
     */
    public boolean gameOver() {
        return this.isOver;
    }
    
    /**
     * Method to determine if the game is over with a draw
     * @return true if the game is a draw, false otherwise
     */
    public boolean checkForDraw() {
        return !checkForWin()&&getAvailablePositions().isEmpty();
    }
    
    /**
     * Method to determine if the game is over with a winner
     * @return true if the game is over and one player wins, false otherwise
     */
    public boolean checkForWin() {
        // if three in a row that are filled and sum to 15, then win
        
        // check rows 
        for(int row = 1; row<=3;row++) {
            if (!isAvailable(row,1) && !isAvailable(row,2) && !isAvailable(row,3) && board.getRowSum(row)==TARGET) {
                return true;
            }
        }
        
        //check columns
        for(int column = 1; column<=3; column++) {
            if (!isAvailable(1,column) && !isAvailable(2,column) && !isAvailable(3,column) && board.getColumnSum(column)==TARGET) {
                return true;
            }
            
        }
        
        //check downward diag
        if (!isAvailable(1,1) && !isAvailable(2,2) && !isAvailable(3,3) && board.getDownwardDiagonalSum()==TARGET) {
            return true;
        }        
        
        //check upward diag
        if (!isAvailable(3,1) && !isAvailable(2,2) && !isAvailable(1,3) && board.getUpwardDiagonalSum()==TARGET) {
            return true;
        }    
        
        return false;
    }
    
    /**
     * Method for getting game state as a String for printing
     * @return A String representation of the current game state, including available values for each player and the current board
     */
    public String toString() {
        String gameString = "****************\n";
        gameString = gameString + "Odd Player Remaining: ";

        gameString = gameString+oddAvailable.toString();
        gameString = gameString + "\n";
        gameString = gameString + "Even Player Remaining: ";

        gameString = gameString+evenAvailable.toString();
        gameString = gameString + "\n\n";
        
        // current board
        for(int row = 1; row <= BOARD_SIZE; row++) {
            for (int column = 1; column <= BOARD_SIZE; column++) {
                int value = board.getValue(row, column);
                if (value>0) {
                    //value
                    gameString = gameString+" "+value+" ";
                } else {
                    //no value, print blank spot/dash
                    gameString = gameString+" - ";
                }
            }
            gameString = gameString+"\n";
        }
        gameString = gameString+"\n****************\n";
        return gameString;
        
    }
        
    
    
    
    
}

