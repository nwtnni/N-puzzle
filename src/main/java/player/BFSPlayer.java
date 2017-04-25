package player;

import java.util.Comparator;

import puzzle.AbstractPuzzle;

import util.PuzzleNode;

public class BFSPlayer extends AbstractBFSPlayer {

    private class Heuristic implements Comparator<PuzzleNode> {
    
        @Override
        public int compare(PuzzleNode a, PuzzleNode b) {

            int aDepth = a.accept(node -> {
                return node.getDepth();
            });

            int bDepth = b.accept(node -> {
                return node.getDepth();
            });

            return aDepth - bDepth;
        }
    
    }

    public BFSPlayer(AbstractPuzzle puzzle) {
        super(puzzle);
        heuristic = new Heuristic();
    }
}
