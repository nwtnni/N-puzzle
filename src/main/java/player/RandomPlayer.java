package player;

import java.util.List;
import java.util.Random;

import puzzle.AbstractPuzzle;

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
    public void step() {
        List<Integer> moves = puzzle.validMoves();
        int tile = r.nextInt(moves.size());
        puzzle.move(moves.get(tile));
    }
}
