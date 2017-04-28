package util;

import java.util.ArrayList;
import java.util.List;

import puzzle.AbstractPuzzle;
import puzzle.Move;

/*
 * Tree node wrapper class for AbstractPuzzle.
 */
public class PuzzleNode implements Evaluable {

    private AbstractPuzzle puzzle;
    private int depth;
    private Move prev;

    private List<PuzzleNode> next;

    public PuzzleNode(AbstractPuzzle ap, int d, Move m) {
        puzzle = ap;
        depth = d; 
        prev = m;
    }

    /*
     * Expands this node's children. Returns
     * the number of children generated.
     */
    public int generate() {

        next = new ArrayList<PuzzleNode>();
        List<Move> moves = puzzle.validMoves();

        for (Move mv : moves) {
            next.add(new PuzzleNode(puzzle.move(mv), depth + 1, mv));
        }
    
        return moves.size();
    }

    /*
     * Returns the depth of this node.
     */
    public int depth() {
        return depth; 
    }

    /*
     * Returns the tile at (x, y), indexed from top-left.
     */
    public int get(int x, int y) {
        return puzzle.get(x, y);
    }

    /*
     * Returns a list of this node's children.
     */
    public List<PuzzleNode> children() {
        return next; 
    }

    /*
     * Returns the previous move that lead to this state.
     */
    public Move lastMove() {
        return prev; 
    }

    /*
     * Returns whether or not the associated puzzle is
     * solved.
     */
    public boolean solved() {
        return puzzle.solved(); 
    }

    /*
     * Returns the m, n size of this puzzle as an array.
     */
    public int[] size() {
        return puzzle.size(); 
    }

    /*
     * Visitor design pattern.
     */
    @Override
    public int accept(Evaluator e) {
        return e.evaluate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PuzzleNode)) {
            return false; 
        }

        return puzzle.equals(((PuzzleNode) o).puzzle);
    }

    @Override
    public int hashCode() {
        return puzzle.hashCode(); 
    }

    @Override
    public String toString() {
        return puzzle.toString(); 
    }
}
