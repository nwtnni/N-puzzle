package puzzle;

import player.AbstractPlayer;
import player.BFSPlayer;

public class Main {

    /*
     * Temporary
     */
    public static void main (String[] args) {

        int m;
        int n;
        int moves;

        try {
            m = Integer.parseInt(args[0]);
            n = Integer.parseInt(args[1]);
            moves = Integer.parseInt(args[2]);
        } catch(Exception e){
            System.out.println("Usage: java -jar N-puzzle.jar <m> <n> <moves>");
            System.out.println("This generates an <m> x <n> initialized puzzle, randomized in <moves> turns.");
            return;
        }

        AbstractPuzzle p = new ArrayPuzzle(m, n);
        p.randomize(moves);

        System.out.println("Running BFSPlayer...");
        System.out.println(p);

        AbstractPlayer player = new BFSPlayer(p);

        while (!p.solved()) {
            player.step(); 
            System.out.println(p);
        }
    }

}
