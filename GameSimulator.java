
/**
 * Simulates a numberic tic tac toe game 5000 times, gathering the the win for
 * odd and even, also draws
 * 
 * @author mitzibustamante
 *
 */
public class GameSimulator {

    // Instance variable
    /** Number of time the game has to run for - 5000 */
    private int numberOfGameSimulator;
    /** the board in which the game is being played */
    private NTTTGameState initialGameState;
    /** which side the first player is from (odd or even) */
    private PlayerSide whichPlayerStart;
    /** a place to hold all the win for odd */
    private int oddWins;
    /** a place to hold all the win for even */
    private int evenWins;
    /** a place to hold all the draws */
    private int draws;

    // Constructor
    /**
     * Creates all the object for the game simulator, giving all the instance
     * variable an initialization
     * 
     * @param gameState  : the board on which the game is being played
     * @param sidePlayer : the side (odd or even) that the game is being used for
     */
    public GameSimulator(NTTTGameState gameState, PlayerSide sidePlayer) {
        initialGameState = gameState;
        whichPlayerStart = sidePlayer;
        numberOfGameSimulator = 5000;
        oddWins = 0;
        evenWins = 0;
        draws = 0;

    }

    // methods
    /**
     * When the game is being played 5000 times, records the wins for the odds and
     * for the evens
     * 
     */
    public void compute() {
        Player[] players = new Player[2];
        NTTTGameState gameStateCopy;

        for (int i = 0; i < numberOfGameSimulator; i++) {
            gameStateCopy = new NTTTGameState(initialGameState);

            // starts with the seconds player
            int turn = 1;

            players[0] = new RandomPlayer(whichPlayerStart);

            // Determines what the second player should be - odd or even
            if (whichPlayerStart.equals(PlayerSide.ODD)) {
                players[1] = new RandomPlayer(PlayerSide.EVEN);

            } else if (whichPlayerStart == PlayerSide.EVEN) {
                players[1] = new RandomPlayer(PlayerSide.ODD);

            }
            while (!gameStateCopy.gameOver()) {
                players[turn % 2].getNextMove(gameStateCopy);
                turn = turn + 1;
            }
            if (gameStateCopy.checkForWin()) {
                if (gameStateCopy.getWinner() == (PlayerSide.ODD)) {
                    oddWins = oddWins + 1;
                } else if (gameStateCopy.getWinner() == (PlayerSide.EVEN)) {
                    evenWins = evenWins + 1;
                } else if (gameStateCopy.checkForDraw()) {
                    draws = draws + 1;
                }
            }

        }
    }

    /**
     * Gets the value of the odds wins
     * 
     * @return : the number of wind by odd
     */
    public int getOddWins() {
        return oddWins;
    }

    /**
     * Gets the value of the evens wins
     * 
     * @return : the number of wind by even
     */
    public int getEvenWins() {
        return evenWins;
    }

    /**
     * Gets the value of the draws
     * 
     * @return : the number of draws
     */
    public int getDraws() {
        return draws;
    }

    /**
     * Gets the value of the time the game should be played
     * 
     * @return : the number of time plays - in this case 5000
     */
    public int getNumberOfGameSimulator() {
        return numberOfGameSimulator;
    }

}