package player;

import java.util.ArrayDeque;
import java.util.Deque;

import heuristic.Heuristic;

import puzzle.AbstractPuzzle;
import puzzle.Move;

import util.PuzzleNode;

/*
 * Represents a program that solves the n-puzzle
 * using iterative depth-first search.
 */
public class IDSPlayer extends AbstractPlayer {

    private PuzzleNode root;
    private Deque<Move> moves;
    private Heuristic heuristic;

    public IDSPlayer(AbstractPuzzle puzzle, Heuristic h) {
        super(puzzle);
        root = new PuzzleNode(puzzle, 0, null, null);
        moves = new ArrayDeque<Move>();
        heuristic = h;
    }

    @Override
    public void solve() {
        getMoves();
        while (!moves.isEmpty()) {
            puzzle.inPlaceMove(moves.pop());
        }
    }

    @Override
    public void step() {
        if (moves.isEmpty()) {
            getMoves();
        }
        puzzle.inPlaceMove(moves.pop());
    }

    // Helper method for IDS
    protected void getMoves() {
        int depth = 0;
        while (depth >= 0) {
            depth = DFS(root, depth);
        }
        return;
    }

    // Depth-first search
    protected int DFS(PuzzleNode root, int maxDepth) {

        int score = heuristic.evaluate(root);
        if (score > maxDepth) {
            return score; 
        }

        if (root.solved()) {
            moves.push(root.lastMove());
            return -1;
        }

        int min = Integer.MAX_VALUE;
        for (PuzzleNode next : root.generate()){
            int result = DFS(next, maxDepth);
            if (result >= 0) {
                min = (result < min) ? result : min;
            } else {
                moves.push(next.lastMove());
                return -1;
            }
        }
        return min;
    }
}
