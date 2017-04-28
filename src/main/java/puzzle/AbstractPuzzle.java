package puzzle;

import java.util.List;

/*
 * A sliding puzzle game.
 */
public abstract class AbstractPuzzle {

    protected int m;
    protected int n;

    /*
     * Randomizes this puzzle to a solvable configuration
     * in the given number of moves.
     */
    public abstract void randomize(int moves);

    /*
     * Moves this AbstractPuzzle in place. Returns true
     * if successful, or false otherwise.
     */
    public abstract boolean inPlaceMove(Move m);

    /*
     * Returns a new AbstractPuzzle that is the result
     * of moving the given tile, or null if the operation
     * was invalid.
     */
    public abstract AbstractPuzzle move(Move m);

    /*
     * Returns an array of this board's current valid moves.
     */
    public abstract List<Move> validMoves();

    /*
     * Returns true if solved.
     */
    public abstract boolean solved();

    /*
     * Returns this array's size as an array [m, n].
     */
    public abstract int[] size();

    /*
     * Returns the tile at (x, y), indexed from top-left.
     * If tile doesn't exist, returns -1.
     */
    public abstract int get(int i, int j);
}
