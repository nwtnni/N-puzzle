package player;

import puzzle.AbstractPuzzle;

/*
 * Represents a program that solves the n-puzzle.
 */
public abstract class AbstractPlayer {

    protected AbstractPuzzle puzzle;

    /*
     * Gives a puzzle to this new player.
     */
    public AbstractPlayer(AbstractPuzzle ap) {
        puzzle = ap; 
    }

    /*
     * Solves the puzzle. 
     */
    public abstract void solve();

    /*
     * Takes one step toward solving the puzzle.
     */
    public abstract void step();
} 
