package heuristic;

import puzzle.Point;

import util.PuzzleNode;

/*
 * Represents the Manhattan distance heuristic.
 */
public class Manhattan extends Heuristic {

    @Override
    public int evaluate(PuzzleNode node) {

        int[] size = node.size();
        int counter = 0;

        for (int y = 0, i = 0; y < size[1]; y++) {
            for (int x = 0; x < size[0] && i < size[0] * size[1] - 1; x++, i++) {
                Point actual = node.find(i);
                counter += Math.abs(x - actual.x);
                counter += Math.abs(y - actual.y);
            }
        }

        return counter + node.getDepth();
    }
}

