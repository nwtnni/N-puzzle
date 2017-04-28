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
    private PuzzleNode prevPuzzle;

    public PuzzleNode(AbstractPuzzle ap, int d, PuzzleNode p, Move m) {
        puzzle = ap;
        depth = d; 
        prev = m;
        prevPuzzle = p;
    }

    /*
     * Expands this node's children. Returns the all valid
     * successors of the current puzzle state.
     */
    public List<PuzzleNode> generate() {
        List<PuzzleNode> next = new ArrayList<PuzzleNode>();
        List<Move> moves = puzzle.validMoves();
        for (Move mv : moves) {
            next.add(new PuzzleNode(puzzle.move(mv), depth + 1, this, mv));
        }
        return next;
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
     * Returns the previous move that lead to this state.
     */
    public Move lastMove() {
        return prev; 
    }
    
    /*
     * Returns the previous puzzle state.
     */
    public PuzzleNode lastPuzzle() {
        return prevPuzzle; 
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
