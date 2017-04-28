package player;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
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
        root = new PuzzleNode(puzzle, 0, null);
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
        HashMap<PuzzleNode, PuzzleNode> retrace = new HashMap<PuzzleNode, PuzzleNode>();

        stack.push(root);

        while (!stack.isEmpty()) {

            PuzzleNode n = stack.pop(); 

            // Keep searching
            if (!n.solved()) {

                if (n.depth() >= maxDepth) {
                    continue;
                } else {
                    n.generate();
                }

                for (PuzzleNode next : n.children()) {
                    if (found.contains(next)) {
                        continue;
                    } else {
                        found.add(next);
                        stack.push(next);
                        retrace.put(next, n);
                    }
                }
            // Otherwise retrace steps
            } else {
                PuzzleNode ptr = n;
                while (!ptr.equals(root)) {
                    PuzzleNode prev = retrace.get(ptr);
                    moves.push(ptr.lastMove());
                    ptr = prev;
                }

                return true;
            }
        }

        return false;
    }
}
