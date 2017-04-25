package player;

/*
 * Class for generating players.
 */
public class PlayerFactory {

    /*
     * Returns a HumanPlayer.
     */
    public static AbstractPlayer getHumanPlayer(AbstractPuzzle ap) {
        return new HumanPlayer(ap); 
    }

    /*
     * Returns a BFSPlayer using the naive BFS heuristic.
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
}
