package util;

import java.util.ArrayList;
import java.util.List;

import puzzle.AbstractPuzzle;
import puzzle.Point;

/*
 * Tree node wrapper class for AbstractPuzzle.
 */
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

        next = new ArrayList<PuzzleNode>();
        List<Integer> moves = puzzle.validMoves();

        for (Integer tile : moves) {
            AbstractPuzzle ap = puzzle.copy();
            ap.move(tile);
            next.add(new PuzzleNode(ap, depth + 1));
        }
    
        return moves.size();
    }

    /*
     * Returns the location of the given tile, or null
     * if the tile is invalid.
     */
    public Point find(int tile) {
        return puzzle.find(tile);
    }

    /*
     * Returns the tile at the given position, or null
     * if the position is invalid.
     */
    public Integer get(int x, int y) {
        return puzzle.get(x ,y);
    }

    /*
     * Returns the depth of this node.
     */
    public int getDepth() {
        return depth; 
    }

    /*
     * Returns a list of this node's children.
     */
    public List<PuzzleNode> getChildren() {
        return next; 
    }

    /*
     * Returns true if the associated puzzle is solved,
     * or false otherwise.
     */
    public boolean solved() {
        return puzzle.solved();
    }

    /*
     * Returns the necessary move to get from prev to
     * this puzzle state.
     */
    public Integer diff(PuzzleNode prev) {
        return puzzle.diff(prev.puzzle);
    }

    /*
     * Returns the size of the associated puzzle.
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
