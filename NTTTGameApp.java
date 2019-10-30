import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

/**
 * Driver class for NTTT game. 
 * @author Mitzi Bustamante
 *
 */
public class NTTTGameApp {

    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        String oddPlayerSelection;
        String evenPlayerSelection;
        Player[] players = new Player[2];
        int turn = 0;
        NTTTGameState board = new NTTTGameState();

        System.out.println("Welcome to Numerical Tic-Tac-Toe.");
        while (true) {
            System.out.println("Please select an ODD player");
            System.out.println("1) Human ");
            System.out.println("2) Random+ ");
            System.out.println("3) MonteCarlo ");
            System.out.println("4) MonteCarloFJ");
            System.out.print("Your selection: ");
            oddPlayerSelection = keyboard.nextLine();
            if (oddPlayerSelection.equals("1")) {
                players[0] = new HumanPlayer(PlayerSide.ODD);
            } else if (oddPlayerSelection.equals("2")) {
                players[0] = new RandomPlayer(PlayerSide.ODD);
            } else if (oddPlayerSelection.equals("3")) {
                players[0] = new MonteCarloPlayer(PlayerSide.ODD);
            } else if (oddPlayerSelection.contentEquals("4")) {
                players[0] = new MonteCarloPlayerFJ(PlayerSide.ODD);
            } else {
                break;
            }
            System.out.println("Please select an EVEN player");
            System.out.println("1) Human ");
            System.out.println("2) Random+ ");
            System.out.println("3) MonteCarlo ");
            System.out.println("4) MonteCarloFJ");
            System.out.print("Your selection: ");
            evenPlayerSelection = keyboard.nextLine();
            if (evenPlayerSelection.equals("1")) {
                players[1] = new HumanPlayer(PlayerSide.EVEN);
            } else if (evenPlayerSelection.equals("2")) {
                players[1] = new RandomPlayer(PlayerSide.EVEN);
            } else if (evenPlayerSelection.equals("3")) {
                players[1] = new MonteCarloPlayer(PlayerSide.EVEN);
            } else if (evenPlayerSelection.equals("4")) {
                players[1] = new MonteCarloPlayerFJ(PlayerSide.EVEN);
            } else {
                break;
            }
            while (!board.gameOver()) {
                System.out.print(board);
                players[turn % 2].getNextMove(board);
                turn = turn + 1;
            }
            System.out.print(board);

            if (board.checkForWin()) {
                System.out.println("Winner " + board.getWinner());
                break;
            } else {
                System.out.println("DRAW");
                break;
            }
        }
    }
}


