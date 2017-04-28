package player;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import puzzle.AbstractPuzzle;
import puzzle.Move;

/*
 * Represents a program that picks random moves.
 */
public class RandomPlayer extends AbstractPlayer {

    Random r;

    public RandomPlayer(AbstractPuzzle ap) {
        super(ap);
        r = new Random();
    }

    @Override
    public void solve() {
        while (!puzzle.solved()) {
            step(); 
        }
    }

    @Override
    public HashMap<String, Double> stats() {
        return new HashMap<String, Double>(); 
    }

    @Override
    public void step() {
        List<Move> moves = puzzle.validMoves();
        int tile = r.nextInt(moves.size());
        puzzle.inPlaceMove(moves.get(tile));
    }
}
