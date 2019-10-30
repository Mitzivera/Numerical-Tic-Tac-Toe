import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

/**
 * Creating the class for MonteCarloPlayerFJ using the game simulator with fork
 * join, implementing player
 * 
 * @author mitzibustamante
 *
 */
public class MonteCarloPlayerFJ implements Player {

    // Instance variable
    /** the side of player that is starting the game */
    private PlayerSide whichPlayer;

    // Constructor
    /**
     * Creates the object for MonteCorlos that uses the game simulator to creates
     * the 5000 games
     * 
     * @param side : the side of the player that is using MonteCarloPlayerFJ
     */
    public MonteCarloPlayerFJ(PlayerSide side) {
        whichPlayer = side;
    }

    // methods
    /**
     * This moves the the best next move, by using the formula given by the
     * professor, (win -loss)/5000. After running the game simulation to check which
     * it the best move, then it gets the position and the value of the best score.
     */
    @Override
    public void getNextMove(NTTTGameState gameState) {
        ArrayList<Integer> availablePositions = gameState.getAvailablePositions();
        ArrayList<Integer> availableValues = gameState.getAvailableValues(whichPlayer);
        GameSimulatorFJ tryMoves;
        int bestPosition = 0;
        int bestValues = 0;
        double win = 0;
        double bestMove = -2;

        for (int i = 0; i < availablePositions.size(); i++) {
            for (int j = 0; j < availableValues.size(); j++) {

                // Creates a copy of game board
                NTTTGameState copyOfBoard = new NTTTGameState(gameState);
                tryMoves = new GameSimulatorFJ(copyOfBoard, whichPlayer, 5000);

                // puts in the position and the value

                copyOfBoard.makeMove(availablePositions.get(i), availableValues.get(j));

                // compute the moves

                ForkJoinPool.commonPool().invoke(tryMoves);

                // Set the position , value and the best score
                if (whichPlayer == PlayerSide.EVEN) {
                    win = tryMoves.getEvenWins() - tryMoves.getOddWins() / 5000.00;
                    if (win > bestMove) {
                        // max formula
                        bestMove = win;
                        // update position and value
                        bestPosition = availablePositions.get(i);
                        bestValues = availableValues.get(j);
                    }

                } else if (whichPlayer == PlayerSide.ODD) {
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
        // tells the players that MonteCarlos is thinking before making the move
        System.out.println("MonteCarloFJ is thinking...");
        // makes the actual move
        gameState.makeMove(bestPosition, bestValues);

    }

}
