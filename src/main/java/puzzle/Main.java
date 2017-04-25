package puzzle;

import player.AbstractPlayer;
import player.Human;

public class Main {

    /*
     * Temporary
     */
    public static void main (String[] args) {

        int m;
        int n;

        try {
            m = Integer.parseInt(args[0]);
            n = Integer.parseInt(args[1]);
        } catch(Exception e){
            System.out.println("Usage: java -jar N-puzzle.jar <m> <n>");
            System.out.println("This generates an m x n initialized puzzle.");
            return;
        }

        AbstractPuzzle p = new ArrayPuzzle(m, n);
        p.randomize();

        AbstractPlayer player = new Human(p);
        player.solve();
    }

}
