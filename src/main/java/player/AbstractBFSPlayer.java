package player;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

import puzzle.AbstractPuzzle;

import util.PuzzleNode;

/*
 * Represents a program that solves the n-puzzle
 * using some variation on BFS search.
 */
public abstract class AbstractBFSPlayer extends AbstractPlayer {

    protected PuzzleNode root;
    protected Comparator<PuzzleNode> heuristic;
    protected Deque<Integer> moves;

    public AbstractBFSPlayer(AbstractPuzzle puzzle) {
        super(puzzle);
        root = new PuzzleNode(puzzle, 0);
        moves = new ArrayDeque<Integer>();
    }

    @Override
    public void solve() {
        getMoves();
        while (!moves.isEmpty()) {
            puzzle.move(moves.pop());
        }
    }

    @Override
    public void step() {
        if (moves.isEmpty()) {
            getMoves();
        }
        puzzle.move(moves.pop());
    }

    // Helper method for BFS
    protected void getMoves() {

        HashSet<PuzzleNode> found = new HashSet<PuzzleNode>();
        PriorityQueue<PuzzleNode> q = new PriorityQueue<PuzzleNode>(heuristic);
        HashMap<PuzzleNode, PuzzleNode> retrace = new HashMap<PuzzleNode, PuzzleNode>();

        q.add(root);

        while (!q.isEmpty()) {

            PuzzleNode n = q.poll(); 

            // Keep searching
            if (!n.solved()) {

                n.generate();

                for (PuzzleNode next : n.getChildren()) {
                    if (found.contains(next)) {
                        continue;
                    } else {
                        found.add(next);
                        q.add(next);
                        retrace.put(next, n);
                    }
                }
            // Otherwise retrace steps
            } else {

                PuzzleNode ptr = n;

                while (!ptr.equals(root)) {
                    PuzzleNode prev = retrace.get(ptr);
                    moves.push(ptr.diff(prev));
                    ptr = prev;
                }

                return;
            }
        }

    }
}
