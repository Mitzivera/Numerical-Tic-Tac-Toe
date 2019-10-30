import java.util.ArrayList;
import java.util.Random;

/**
 * This class picks a random position and value to put the random 
 * value in that random position. However if the game can be done in one 
 * move, the random player should make that move. Most of the code was 
 * provided by Professor Yilek.
 * 
 * @author mitzibustamante
 *
 */
public class RandomPlayer implements Player {
    // instance variable
    
    /** An array list of integer */
    private static final ArrayList<Integer> availablaPositionsCopy = null;
    /**  which side the first player is from (odd or even) */
    private PlayerSide side;

    // Constructor
    /**
     * Creates the object for random 
     * @param side : the side that the player is playing from 
     */
    public RandomPlayer(PlayerSide side) {
        this.side = side;
    }

    // Methods
    /**
     * This classes get a random move, but when it can win in one move then it
     * should make that move.
     * 
     * @param gameState: get the board that the player are using
     */
    public void getNextMove(NTTTGameState gameState) {

        ArrayList<Integer> availablePositions = gameState.getAvailablePositions();
        ArrayList<Integer> availableValues = gameState.getAvailableValues(side);
        // You should put code here

        
        // loops through available position and value
        for (int i = 0; i < availablePositions.size(); i++) {
            for (int j = 0; j < availableValues.size(); j++) {

             // gets a copy of the board that the players are using
                NTTTGameState copyOfBoard = new NTTTGameState(gameState);
                
                // makes the moves in the copy of the board
                copyOfBoard.makeMove(availablePositions.get(i), availableValues.get(j));

                // check if it won in that one move and makes that move in the real board
                if (copyOfBoard.checkForWin() == true) {
                    gameState.makeMove(availablePositions.get(i), availableValues.get(j));
                    return;
                }

            }
        }
        // Dr. Yilek provided code starts here
        Random randomGenerator = new Random();

        int chosenPositionIndex = randomGenerator.nextInt(availablePositions.size());
        int chosenValueIndex = randomGenerator.nextInt(availableValues.size());

        int positionToPlay = availablePositions.get(chosenPositionIndex);
        int valueToPlay = availableValues.get(chosenValueIndex);
        gameState.makeMove(positionToPlay, valueToPlay);
    }
}
