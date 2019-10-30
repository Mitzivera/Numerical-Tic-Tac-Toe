import java.util.concurrent.RecursiveAction;

/**
 * This class creates the game simulator fork join for monteCarlosFJ Its used
 * divide and conquer, making it faster than not using it
 * 
 * @author mitzibustamante
 *
 */
public class GameSimulatorFJ extends RecursiveAction {

    // instance variable
    /** set the number of simulation that need to be done */
    private int numberOfGameSimulator;
    /** the initial game board that is being played */
    private NTTTGameState initialGameState;
    /** which player start playing first (odd or even) */
    private PlayerSide whichPlayerStart;
    /** a variable to put the odd wins */
    private int oddWins;
    /** a variable to put the even wins */
    private int evenWins;
    /** a variable to put the draws in ( where both odd and even don't win) */
    private int draws;

    // Constructor
    /**
     * Creates the object of game simulation, using fork join, using divide and
     * conquer in which divides the 5000 simulation into two and keeps repeating
     * that until it hits the base case of 1. Add the odd, even and draws from both
     * side - right and left- to get the total. That total is later used in
     * MonteCarlo
     * 
     * @param gameState    : the state of the board that is being played
     * @param sidePlayer   : the player side (odd or even) the one that starts with
     *                     the game
     * @param numberOfGame : the number of simulation that has to be done in this
     *                     case is 5000
     */
    public GameSimulatorFJ(NTTTGameState gameState, PlayerSide sidePlayer, int numberOfGame) {
        initialGameState = gameState;
        whichPlayerStart = sidePlayer;
        numberOfGameSimulator = numberOfGame;
        oddWins = 0;
        evenWins = 0;
        draws = 0;
    }

    // Methods
    /**
     * Using fork join to speed up the action of playing 5000 games from a positions
     * Used divide and conquer to speed up the process
     */
    @Override
    public void compute() {
        // creates a copy of the board that is being played
        NTTTGameState gameStateCopy;
        gameStateCopy = new NTTTGameState(initialGameState);
        // start with the second player instead than the first one
        int turn = 1;
        Player[] players;
        players = new Player[2];

        // base case
        if (numberOfGameSimulator == 1) {
            players[0] = new RandomPlayer(whichPlayerStart);

            if (whichPlayerStart == PlayerSide.ODD) {
                players[1] = new RandomPlayer(PlayerSide.EVEN);
            } else if (whichPlayerStart == PlayerSide.EVEN) {
                players[1] = new RandomPlayer(PlayerSide.ODD);
            }
            while (!gameStateCopy.gameOver()) {
                players[turn % 2].getNextMove(gameStateCopy);
                turn = turn + 1;
            }
            // checks if there is a winner of either side
            if (gameStateCopy.getWinner() == PlayerSide.ODD) {
                oddWins = oddWins + 1;
            } else if (gameStateCopy.getWinner() == PlayerSide.EVEN) {
                evenWins = evenWins + 1;
            } else {
                draws = draws + 1;
            }
        } else {
            // creates the game object, two of them for the right and the left
            GameSimulatorFJ left = new GameSimulatorFJ(gameStateCopy, whichPlayerStart, numberOfGameSimulator / 2);
            GameSimulatorFJ right = new GameSimulatorFJ(gameStateCopy, whichPlayerStart,
                    numberOfGameSimulator - (numberOfGameSimulator / 2));

            left.fork();
            right.compute();
            left.join();
            // add both side to get a grand total from both sides
            oddWins = left.oddWins + right.oddWins;
            evenWins = left.evenWins + right.evenWins;
            draws = left.draws + right.draws;
        }
    }

    /**
     * A getter to get the odd number of wins from the game
     * 
     * @return : the number odd wins
     */
    public int getOddWins() {
        return oddWins;
    }

    /**
     * A getter to get the even number of wins from the game
     * 
     * @return : the number even wins
     */
    public int getEvenWins() {
        return evenWins;
    }

    /**
     * A getter to get the draw from the game
     * 
     * @return : the number of draws
     */
    public int getDraws() {
        return draws;
    }

    /**
     * A getter to get the number of simulation that is being used
     * 
     * @return : the number of simulation being made
     */
    public double getNumberOfGameSimulator() {
        return numberOfGameSimulator;
    }
}
