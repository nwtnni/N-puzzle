package player;

import java.util.Scanner;

import puzzle.AbstractPuzzle;
import puzzle.Move;

/*
 * The human player queries System.in for each move, and
 * also prints the board and helpful messages
 * for the player's benefit.
 *
 * Be forwarned: this is a relatively clumsy solution, and 
 * the player may or may not actually solve the puzzle.
 */
public class HumanPlayer extends AbstractPlayer {

    Scanner s;

    public HumanPlayer(AbstractPuzzle ap) {
        super(ap);
        s = new Scanner(System.in);
    }

    @Override
    public void solve() {

        s = new Scanner(System.in);
        System.out.println("Enter the tile to move, or 'quit' to quit.\n"); 

        while (true) {
            if (puzzle.solved()) {
                s.close();
                return;
            }
            step();
        }
    }

    @Override
    public void step() {
        while (true) {
            try {
                Move m = null;
                while (m == null) {
                    switch (s.next()) {
                        case "u": 
                            m = Move.U;
                            break;
                        case "d":
                            m = Move.D;
                            break;
                        case "l":
                            m = Move.L;
                            break;
                        case "r":
                            m = Move.R;
                            break;
                        default:
                            System.out.println("Must be one of u, d, l, r");
                    }
                }
                if (!puzzle.validMoves().contains(m)) {
                    System.out.println("Invalid move."); 
                } else {
                    puzzle.inPlaceMove(m);
                    System.out.print("\n"); 
                    return;
                }
            } catch(Exception e){
                System.out.println("Invalid move."); 
            }
        }
    }
}
