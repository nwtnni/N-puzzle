package player;

import puzzle.AbstractPuzzle;

/*
 * Represents a program that solves the n-puzzle.
 */
public abstract class Player {

    protected AbstractPuzzle puzzle;

    /*
     * Gives a puzzle to this new player.
     */
    public Player(AbstractPuzzle ap) {
        puzzle = ap; 
    }

    /*
     * Solves the given puzzle.
     */
    public abstract void solve();
} 
