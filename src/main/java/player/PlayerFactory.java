package player;

import heuristic.Linear;
import heuristic.Manhattan;
import heuristic.Naive;
import heuristic.OutOfPlace;

import puzzle.AbstractPuzzle;

/*
 * Class for generating players.
 */
public class PlayerFactory {

    /*
     * Returns a player taking input from console.
     */
    public static AbstractPlayer getHuman(AbstractPuzzle ap) {
        return new HumanPlayer(ap); 
    }

    /*
     * Returns a player using naive breadth-first search.
     */
    public static AbstractPlayer getNaiveBFS(AbstractPuzzle ap) {
        return new BFSPlayer(ap, new Naive());
    }

    /*
     * Returns a player using A* search with the out of place 
     * heuristic.
     */
    public static AbstractPlayer getOutOfPlaceBFS(AbstractPuzzle ap) {
        return new BFSPlayer(ap, new OutOfPlace());
    }

    /*
     * Returns a player using A* search with the Manhattan distance
     * heuristic.
     */
    public static AbstractPlayer getManhattanBFS(AbstractPuzzle ap) {
        return new BFSPlayer(ap, new Manhattan(true));
    }

    /*
     * Returns a player using A* search with the Manhattan distance
     * heuristic.
     */
    public static AbstractPlayer getFastManhattanBFS(AbstractPuzzle ap) {
        return new BFSPlayer(ap, new Manhattan(false));
    }

    /*
     * Returns a player using A* search with the Manhattan distance
     * plus linear conflicts heuristic.
     */
    public static AbstractPlayer getManhattanConflictBFS(AbstractPuzzle ap) {
        return new BFSPlayer(ap, new Linear(true));
    }

    /*
     * Returns a player using A* search with the Manhattan distance
     * plus linear conflicts heuristic.
     */
    public static AbstractPlayer getFastManhattanConflictBFS(AbstractPuzzle ap) {
        return new BFSPlayer(ap, new Linear(false));
    }

    /*
     * Returns a player using naive iterative-deepening search.
     */
    public static AbstractPlayer getNaiveIDS(AbstractPuzzle ap) {
        return new IDSPlayer(ap, new Naive());
    }

    /*
     * Returns a player using IDA* search with the out of place
     * heuristic.
     */
    public static AbstractPlayer getOutOfPlaceIDS(AbstractPuzzle ap) {
        return new IDSPlayer(ap, new OutOfPlace());
    }

    /*
     * Returns a player using IDA* search with the Manhattan distance
     * heuristic.
     */
    public static AbstractPlayer getManhattanIDS(AbstractPuzzle ap) {
        return new IDSPlayer(ap, new Manhattan(true));
    }

    /*
     * Returns a player using A* search with the Manhattan distance
     * plus linear conflicts heuristic.
     */
    public static AbstractPlayer getManhattanConflictIDS(AbstractPuzzle ap) {
        return new IDSPlayer(ap, new Linear(true));
    }

    /*
     * Returns a player playing randomly.
     */
    public static AbstractPlayer getRandom(AbstractPuzzle ap) {
        return new RandomPlayer(ap);
    }
}
