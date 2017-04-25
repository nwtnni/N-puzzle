package puzzle;

import java.util.List;

/*
 * A sliding puzzle game.
 */
public abstract class AbstractPuzzle {

    protected int m;
    protected int n;

    /*
     * Randomizes this puzzle to a solvable configuration.
     */
    public abstract void randomize();

    /*
     * Randomizes this puzzle to a solvable configuration
     * in the given number of moves.
     */
    public abstract void randomize(int moves);

    /*
     * Moves the given tile.
     *
     * @return True if successful; false if invalid move.
     */
    public abstract boolean move(int i);

    /*
     * Returns a list of this board's current valid moves.
     */
    public abstract List<Integer> validMoves();

    /*
     * Returns the tile at the given location, or null
     * if invalid position.
     */
    public abstract Integer get(Point p);
    public abstract Integer get(int x, int y);

    /*
     * Returns a copy of this puzzle.
     */
    public abstract AbstractPuzzle copy();

    /*
     * Returns true if solved.
     */
    public abstract boolean solved();
}
