package util;

import java.util.List;

import puzzle.AbstractPuzzle;

public class PuzzleNode {

    private AbstractPuzzle puzzle;

    private List<PuzzleNode> next;

    public PuzzleNode(AbstractPuzzle ap) {
        puzzle = ap;
    }

    /*
     * Expands this node's children. Returns
     * the number of children generated.
     */
    public int generate() {

        List<Integer> moves = puzzle.validMoves();

        for (Integer tile : moves) {
            AbstractPuzzle ap = puzzle.copy();
            ap.move(tile);
            next.add(new PuzzleNode(ap));
        }
    
        return moves.size();
    }

}
