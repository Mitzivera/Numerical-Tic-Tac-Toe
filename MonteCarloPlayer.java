import java.util.ArrayList;

/**
 * It's the class in where MonteCarlo is used. This class runs the possible
 * position in where the best move can make it. When this class is being used
 * the player using it first is always going to win
 * 
 * @author mitzibustamante
 *
 */
public class MonteCarloPlayer implements Player {
    // instance variable
    /** Which side the player is staring from (odd or even ) */
    private PlayerSide side;

    // Constructor
    /**
     * Creates an object of MonteCarloPlayer
     * 
     * @param side : the side in which the player is using it is (odd or even)
     */
    public MonteCarloPlayer(PlayerSide side) {
        this.side = side;
    }

    // method

    /**
     * This method makes the game, but first it creates a copy of the game, makes a
     * move and then it used the GameSimulator class to run the game 500 time,
     * depending on what the player side is it's going to make the best move for
     * that side. It uses the formula (win - loss)/5000 for whichever side is using
     * the MonteCarloPlayer class; After knowing with score is better it put in that
     * available position with the available values
     * 
     * @param gameState : the board in which the game is being used to play the game
     */
    public void getNextMove(NTTTGameState gameState) {
        ArrayList<Integer> availablePositions = gameState.getAvailablePositions();
        ArrayList<Integer> availableValues = gameState.getAvailableValues(side);
        GameSimulator tryMoves;
        int bestPosition = 0;
        int bestValues = 0;
        double win = 0;
        double bestMove = -2;

        for (int i = 0; i < availablePositions.size(); i++) {
            for (int j = 0; j < availableValues.size(); j++) {

                // Creates a copy of game board
                NTTTGameState copyOfBoard = new NTTTGameState(gameState);
                tryMoves = new GameSimulator(copyOfBoard, side);

                // puts in the position and the value

                copyOfBoard.makeMove(availablePositions.get(i), availableValues.get(j));

                // compute the moves

                tryMoves.compute();

                // Set the position , value and the best score - also has to be inside a loop
                if (side == PlayerSide.EVEN) {
                    win = tryMoves.getEvenWins() - tryMoves.getOddWins() / 5000.00;
                    if (win > bestMove) {
                        // max formula
                        bestMove = win;
                        // update position and value
                        bestPosition = availablePositions.get(i);
                        bestValues = availableValues.get(j);
                    }

                } else if (side == PlayerSide.ODD) {
                    win = tryMoves.getOddWins() - tryMoves.getEvenWins() / 5000.00;
                    if (win > bestMove) {
                        // max formula
                        bestMove = win;
                        // update the best position and values
                        bestPosition = availablePositions.get(i);
                        bestValues = availableValues.get(j);
                    }

                }
            }
        }
        // tells the user that the player is thinkig
        System.out.println("MonteCarlo is thinking...");
        // actually makes the official move to the board, not the copy
        gameState.makeMove(bestPosition, bestValues);
    }
}
