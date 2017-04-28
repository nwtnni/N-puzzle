package heuristic;

import util.PuzzleNode;

/*
 * Represents the Manhattan distance heuristic.
 */
public class Manhattan extends Heuristic {

    @Override
    public int evaluate(PuzzleNode n) {

        int counter = 0;
        int[] size = n.size();

        for (int y = 0; y < size[0]; y++) {
            for (int x = 0; x < size[1]; x++) {
                int k = n.get(x, y);
                if (k == size[0] * size[1] - 1) continue;
                counter += Math.abs(k % size[1] - x);
                counter += Math.abs(k / size[1] - y);
            }
        }

        return counter + n.depth();
    }
}

