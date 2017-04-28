package puzzle;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import player.AbstractPlayer;
import player.PlayerFactory;

public class Main {

    public static void usage() {
        System.out.println("Usage: java -jar N-puzzle.jar <M> <N> <MOVES> <PLAYER> <AVG> <RATE>"); 
        System.out.println("<M> x <N> is the dimension of puzzle to create.");
        System.out.println("<MOVES> is the number of moves used to randomize the puzzle.");
        System.out.println("<PLAYER> is one of :");
        System.out.println("\t-b to use naive breadth-first search");
        System.out.println("\t-h to play manually");
        System.out.println("\t-i to use naive iterative depth-first search");
        System.out.println("\t-mi to use IDA* search with the Manhattan distance heuristic (see readme)");
        System.out.println("\t-ma to use A* search with the Manhattan distance heuristic (see readme)");
        System.out.println("\t-fma to use A* search with the Manhattan distance heuristic (see readme)");
        System.out.println("\t-li to use IDA* search with the Manhattan distance plus linear conflict heuristic (see readme)");
        System.out.println("\t-la to use A* search with the Manhattan distance plus linear conflict heuristic (see readme)");
        System.out.println("\t-fla to use A* search with the Manhattan distance heuristic (see readme)");
        System.out.println("\t-oa to use A* search with the out-of-place heuristic (see readme)");
        System.out.println("\t-oi to use IDA* search with the out-of-place heuristic (see readme)");
        System.out.println("\t-r to play randomly");
        System.out.println("<AVG> is one of the following:");
        System.out.println("\t0 to do a step-by-step solve, with pretty-printing");
        System.out.println("\tn to average the solving time taken over n trials");
        System.out.println("<RATE> is the number of steps to display per second for automated solving.");
        System.out.println("Only required if AVG = 0.");
    }

    public static void main (String[] args) {

        int m;
        int n;
        int moves;
        int trials;
        int rate = 1;

        try {
            m = Integer.parseInt(args[0]);
            n = Integer.parseInt(args[1]);
            moves = Integer.parseInt(args[2]);
            trials = Integer.parseInt(args[4]);
            if (trials == 0) {
                rate = Integer.parseInt(args[5]);
            }
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
                player = PlayerFactory.getNaiveBFS(p);
                break;
            case "-la":
                player = PlayerFactory.getManhattanConflictBFS(p);
                break;
            case "-fla":
                player = PlayerFactory.getFastManhattanConflictBFS(p);
                break;
            case "-oa":
                player = PlayerFactory.getOutOfPlaceBFS(p);
                break;
            case "-ma":
                player = PlayerFactory.getManhattanBFS(p);
                break;
            case "-fma":
                player = PlayerFactory.getFastManhattanBFS(p);
                break;
            case "-i":
                player = PlayerFactory.getNaiveIDS(p);
                break;
            case "-li":
                player = PlayerFactory.getManhattanConflictIDS(p);
                break;
            case "-mi":
                player = PlayerFactory.getManhattanIDS(p);
                break;
            case "-oi":
                player = PlayerFactory.getOutOfPlaceIDS(p);
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
                        TimeUnit.MILLISECONDS.sleep(1000 / rate);
                    } catch (Exception e) {}
                }
                System.out.println(p);
            }

            for (String key : player.stats().keySet()) {
                System.out.println(key + player.stats().get(key)); 
            }
        
        } else {

            double total = 0;
            HashMap<String, Double> stats = new HashMap<String, Double>();

            for (int i = 0; i < trials; i++) {
                p.randomize(moves);
                total += timedSolve(player); 
                for (String key : player.stats().keySet()) {
                    Double val = player.stats().get(key);
                    if (stats.containsKey(key)) {
                        stats.put(key, stats.get(key) + val);
                    } else {
                        stats.put(key, val);
                    }
                }
            }

            System.out.println("Average time: " + (total / 1e9 / trials) + " seconds" );
            for (String key : stats.keySet()) {
                System.out.println(key + stats.get(key) / trials); 
            }
        }
    }

    public static double timedSolve(AbstractPlayer ap) {
        double start = System.nanoTime();
        ap.solve();
        return System.nanoTime() - start;
    }
}
