package puzzle;

import java.util.concurrent.TimeUnit;

import player.AbstractPlayer;
import player.PlayerFactory;

public class Main {

    public static void usage() {
        System.out.println("Usage: java -jar N-puzzle.jar <M> <N> <MOVES> <PLAYER> <AVG>"); 
        System.out.println("<M> x <N> is the dimension of puzzle to create.");
        System.out.println("<MOVES> is the number of moves used to randomize the puzzle.");
        System.out.println("<PLAYER> is one of :");
        System.out.println("\t-b to use naive breadth-first search");
        System.out.println("\t-h to play manually");
        System.out.println("\t-i to use iterative depth-first search");
        System.out.println("\t-m to use A* search with the Manhattan distance heuristic (see readme)");
        System.out.println("\t-o to use A* search with the out-of-place heuristic (see readme)");
        System.out.println("\t-r to play randomly");
        System.out.println("<AVG> is one of the following:");
        System.out.println("\t0 to do a step-by-step solve, with pretty-printing");
        System.out.println("\tn to average the solving time taken over n trials");
    }

    public static void main (String[] args) {

        int m;
        int n;
        int moves;
        int trials;

        try {
            m = Integer.parseInt(args[0]);
            n = Integer.parseInt(args[1]);
            moves = Integer.parseInt(args[2]);
            trials = Integer.parseInt(args[4]);
        } catch(Exception e){
            usage();
            return;
        }

        AbstractPuzzle p = new ArrayPuzzle(m, n);
        AbstractPlayer player;

        switch (args[3]) {
            case "-h": 
                player = PlayerFactory.getHuman(p);
                break;
            case "-b":
                player = PlayerFactory.getNaive(p);
                break;
            case "-o":
                player = PlayerFactory.getOutOfPlace(p);
                break;
            case "-m":
                player = PlayerFactory.getManhattan(p);
                break;
            case "-i":
                player = PlayerFactory.getIDS(p);
                break;
            case "-r":
                player = PlayerFactory.getRandom(p);
                break;
            default:
                usage();
                return;
        }

        if (trials == 0) {

            p.randomize(moves);
            System.out.println(p);

            while (!p.solved()) {
                player.step(); 
                if (!args[3].equals("-p")) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (Exception e) {}
                }
                System.out.println(p);
            }
        
        } else {

            double total = 0;

            for (int i = 0; i < trials; i++) {
                p.randomize(moves);
                total += timedSolve(player); 
            }

            System.out.println("Average time: " + (total / 1e9 / trials) + " seconds" );
        }
    }

    public static double timedSolve(AbstractPlayer ap) {
        double start = System.nanoTime();
        ap.solve();
        return System.nanoTime() - start;
    }
}
