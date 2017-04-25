package util;

import java.util.List;

import puzzle.AbstractPuzzle;

public class PuzzleNode implements Evaluable {

    private AbstractPuzzle puzzle;
    private int depth;

    private List<PuzzleNode> next;

    public PuzzleNode(AbstractPuzzle ap, int d) {
        puzzle = ap;
        depth = d; 
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
            next.add(new PuzzleNode(ap, depth + 1));
        }
    
        return moves.size();
    }

    /*
     * Returns the depth of this node.
     */
    public int getDepth() {
        return depth; 
    }

    /*
     * Visitor design pattern.
     */
    public void accept(Evaluator e) {
        e.visit(this);
    }
}
