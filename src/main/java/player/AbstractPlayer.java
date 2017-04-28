package player;

import java.util.HashMap;

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

    /*
     * Prints out any relevant statistics for this player.
     */
    public abstract HashMap<String, Double> stats();

    @Override
    public String toString() {
        return puzzle.toString(); 
    }
} 
