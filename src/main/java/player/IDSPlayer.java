package player;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

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
            depth = DFS(depth);
        }
        return;
    }

    // Depth-first search
    protected int DFS(int maxDepth) {

        HashSet<PuzzleNode> found = new HashSet<PuzzleNode>();
        Deque<PuzzleNode> stack = new ArrayDeque<PuzzleNode>();
        int min = Integer.MAX_VALUE;
        stack.push(root);

        while (!stack.isEmpty()) {

            PuzzleNode n = stack.pop(); 
            int score = heuristic.evaluate(n);

            if (score > maxDepth) {
                min = (score < min) ? score : min;
                continue;
            }

            // Keep searching
            if (!n.solved()) {
                for (PuzzleNode next : n.generate()) {
                    if (!found.contains(next)) {
                        found.add(next);
                        stack.push(next);
                    }
                }
            // Otherwise retrace steps
            } else {
                PuzzleNode ptr = n;
                while (!ptr.equals(root)) {
                    moves.push(ptr.lastMove());
                    ptr = ptr.lastPuzzle();
                }
                return -1;
            }
        }

        return min;
    }
}
