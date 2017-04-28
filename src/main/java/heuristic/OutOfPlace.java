package heuristic;

import util.PuzzleNode;

/*
 * Represents the out-of-place heuristic, which counts
 * how many nodes are out of place in the puzzle.
 */
public class OutOfPlace extends Heuristic { 

    @Override
    public int evaluate(PuzzleNode pn) {
        
        int[] size = pn.size();
        int counter = 0;

        for (int y = 0, i = 0; y < size[0]; y++) {
            for (int x = 0; x < size[1]; x++) {
                if (pn.get(x, y) != i++) {
                    counter++; 
                }
            }
        }
        return counter;
    }
}
