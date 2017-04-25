package player;

import util.PuzzleNode;

/*
 * Represents the out-of-place heuristic, which counts
 * how many nodes are out of place in the puzzle.
 */
public class OutOfPlace extends Heuristic { 

    @Override
    public int evaluate(PuzzleNode node) {
        
        int[] size = node.size();
        int counter = 0;

        for (int y = 0, i = 0; y < size[1]; y++) {
            for (int x = 0; x < size[0] && i < size[0] * size[1] - 1; x++, i++) {
                if (node.get(x, y) != i) {
                    counter++; 
                }
            }
        }
        return counter;
    }
}
