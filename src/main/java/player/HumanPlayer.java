package player;

import java.util.Scanner;

import puzzle.AbstractPuzzle;

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

            System.out.println(puzzle);

            if (puzzle.solved()) {
                s.close();
                return;
            }

            String input = s.next();

            try {
                int i = Integer.parseInt(input);
                if (!puzzle.move(i)) {
                    System.out.println("Invalid move. Enter 'quit' to quit.\n"); 
                } else {
                    System.out.print("\n"); 
                }
            } catch(Exception e){
                if (input.equals("quit")) {
                    s.close();
                    return;
                }
                System.out.println("Invalid move. Enter 'quit' to quit.\n"); 
            }
        }
    }

    @Override
    public void step() {
        while (true) {
            try {
                int i = Integer.parseInt(s.next());
                if (!puzzle.move(i)) {
                    System.out.println("Invalid move."); 
                } else {
                    System.out.print("\n"); 
                    return;
                }
            } catch(Exception e){
                System.out.println("Invalid move."); 
            }
        }
    }
}
