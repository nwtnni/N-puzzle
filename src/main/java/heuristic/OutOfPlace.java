package heuristic;

import util.PuzzleNode;

/*
 * Represents the out-of-place heuristic, which counts
 * how many nodes are out of place in the puzzle.
 */
public class OutOfPlace extends Heuristic { 

    @Override
    public int evaluate(PuzzleNode n) {
        
        int[] size = n.size();
        int counter = 0;

        for (int y = 0, i = 0; y < size[0]; y++) {
            for (int x = 0; x < size[1]; x++) {
                int tile = n.get(x, y);
                if (tile != i++ && tile != size[0] * size[1] - 1) {
                    counter++; 
                }
            }
        }
        return counter + n.depth();
    }
}
