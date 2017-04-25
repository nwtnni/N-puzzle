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
     * Returns the given tile's location, or null if invalid tile.
     */
    public abstract Point find(int tile);

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
     * Returns the single move necessary to transform
     * prev into this puzzle. If more than one move is
     * necessary, or if the puzzles are different sizes,
     * returns null.
     */
    public abstract Integer diff(AbstractPuzzle prev);
    
    /*
     * Returns this puzzle's m x n size as an array {m, n}.
     */
    public int[] size() {
        return new int[] {m, n};
    }

    /*
     * Returns true if solved.
     */
    public abstract boolean solved();
}
