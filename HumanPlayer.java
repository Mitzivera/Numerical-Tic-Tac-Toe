import java.util.ArrayList;
import java.util.Scanner;


/**
 * DO NOT MODIFY unless you find a bug and discuss it with Dr. Yilek first.
 * 
 * @author Scott Yilek, for use in CISC 230
 *
 */
public class HumanPlayer implements Player {
    
    
    /** Which side the human player is playing.  Should be either PlayerSide.EVEN or PlayerSide.ODD */
    private PlayerSide side;
    /** Scanner for getting user input from the keyboard */
    private static Scanner keyboardScanner = new Scanner(System.in);
    
    // Standard constructor taking the side
    /**
     * Creates an object for which the user can move the value to
     * thier desire posotion
     * 
     * @param side : the side in which the player is from
     */
    public HumanPlayer(PlayerSide side) {
        this.side = side;
    }
    
    /** 
     * This method takes the current game state and has the user make a move, ultimately 
     * sending this move to the game state makeMove method
     * 
     * @param gameState The current NTTT game state object
     */
    @Override
    public void getNextMove(NTTTGameState gameState) {
        
        ArrayList<Integer> availableValues = gameState.getAvailableValues(side);
        ArrayList<Integer> availablePositions = gameState.getAvailablePositions();
        int valueToPlay = -1;
        int positionToPlay = -1;
        
        while (!availableValues.contains(valueToPlay)) {
            System.out.print("Choose a value from "+availableValues+" : ");
            valueToPlay = keyboardScanner.nextInt();
        }
        while (!availablePositions.contains(positionToPlay)) {
            System.out.print("Choose an open position from "+availablePositions+" : ");
            positionToPlay = keyboardScanner.nextInt();
        }
        
        gameState.makeMove(positionToPlay, valueToPlay);
        
        
    }

}
