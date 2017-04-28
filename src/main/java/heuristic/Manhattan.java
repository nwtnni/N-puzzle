package heuristic;

import util.PuzzleNode;

/*
 * Represents the Manhattan distance heuristic.
 */
public class Manhattan extends Heuristic {

    private boolean opt;

    public Manhattan(boolean optimal) {
        opt = optimal; 
    }

    @Override
    public int evaluate(PuzzleNode n) {

        int counter = 0;
        int M = n.size()[0];
        int N = n.size()[1];

        for (int y = 0; y < M; y++) {
            for (int x = 0; x < N; x++) {
                int k = n.get(x, y);
                if (k == M * N - 1) continue;
                counter += Math.abs(k % N - x);
                counter += Math.abs(k / N - y);
            }
        }

        return (opt) ? counter + n.depth() : counter;
    }
}

