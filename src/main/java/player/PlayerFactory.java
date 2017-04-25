package player;

import heuristic.Naive;
import heuristic.OutOfPlace;
import heuristic.Manhattan;

import puzzle.AbstractPuzzle;

/*
 * Class for generating players.
 */
public class PlayerFactory {

    /*
     * Returns a HumanPlayer.
     */
    public static AbstractPlayer getHuman(AbstractPuzzle ap) {
        return new HumanPlayer(ap); 
    }

    /*
     * Returns a BFSPlayer using the naive breadth-first search.
     */
    public static AbstractPlayer getNaive(AbstractPuzzle ap) {
        return new BFSPlayer(ap, new Naive());
    }

    /*
     * Returns a BFSPlayer using the out of place heuristic.
     */
    public static AbstractPlayer getOutOfPlace(AbstractPuzzle ap) {
        return new BFSPlayer(ap, new OutOfPlace());
    }


    /*
     * Returns a BFSPlayer using the Manhattan distance heuristic.
     */
    public static AbstractPlayer getManhattan(AbstractPuzzle ap) {
        return new BFSPlayer(ap, new Manhattan());
    }

    /*
     * Returns an IDSPlayer.
     */
    public static AbstractPlayer getIDS(AbstractPuzzle ap) {
        return new IDSPlayer(ap);
    }

    /*
     * Returns a RandomPlayer.
     */
    public static AbstractPlayer getRandom(AbstractPuzzle ap) {
        return new RandomPlayer(ap);
    }
}
