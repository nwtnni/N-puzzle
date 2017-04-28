package player;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

import puzzle.AbstractPuzzle;
import puzzle.Move;

import util.PuzzleNode;

/*
 * Represents a program that solves the n-puzzle
 * using iterative depth-first search.
 */
public class IDSPlayer extends AbstractPlayer {

    protected PuzzleNode root;
    protected Deque<Move> moves;

    public IDSPlayer(AbstractPuzzle puzzle) {
        super(puzzle);
        root = new PuzzleNode(puzzle, 0, null, null);
        moves = new ArrayDeque<Move>();
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
        int depth = 1; 
        while (!DFS(depth++)) {}
        return;
    }

    // Depth-first search
    protected boolean DFS(int maxDepth) {

        HashSet<PuzzleNode> found = new HashSet<PuzzleNode>();
        Deque<PuzzleNode> stack = new ArrayDeque<PuzzleNode>();
        stack.push(root);

        while (!stack.isEmpty()) {
            PuzzleNode n = stack.pop(); 

            if (n.depth() > maxDepth) continue;

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
                return true;
            }
        }

        return false;
    }
}
