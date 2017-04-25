package player;

import util.PuzzleNode;

public class Naive extends Heuristic {

    @Override
    public int evaluate (PuzzleNode n) {
        return n.getDepth(); 
    }
}
