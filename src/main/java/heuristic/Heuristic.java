package heuristic;

import java.util.Comparator;

import util.Evaluator;
import util.PuzzleNode;

/*
 * Represents some heuristic for evaluating the cost of a node.
 */
public abstract class Heuristic implements Comparator<PuzzleNode>, Evaluator {

    @Override
    public int compare(PuzzleNode a, PuzzleNode b) {
        int aValue = a.accept(this);
        int bValue = b.accept(this);
        return aValue - bValue;
    }

    @Override
    public abstract int evaluate(PuzzleNode node);

}
