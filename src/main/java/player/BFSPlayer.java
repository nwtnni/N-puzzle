package player;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.PriorityQueue;

import heuristic.Heuristic;

import puzzle.AbstractPuzzle;
import puzzle.Move;

import util.PuzzleNode;

/*
 * Represents a program that solves the n-puzzle
 * using some variation of breadth-first search.
 */
public class BFSPlayer extends AbstractPlayer {

    protected PuzzleNode root;
    protected Heuristic h;
    protected Deque<Move> moves;

    public BFSPlayer(AbstractPuzzle puzzle, Heuristic heuristic) {
        super(puzzle);
        h = heuristic;
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

    // Helper method for BFS
    protected void getMoves() {

        PriorityQueue<PuzzleNode> q = new PriorityQueue<PuzzleNode>(h);
        HashSet<PuzzleNode> found = new HashSet<PuzzleNode>();
        q.add(root);

        while (!q.isEmpty()) {

            PuzzleNode n = q.poll(); 

            // Keep searching
            if (!n.solved()) {
                for (PuzzleNode next : n.generate()) {
                    if (!found.contains(next)) {
                        found.add(next);
                        q.add(next);
                    }
                }
            // Otherwise retrace steps
            } else {

                PuzzleNode ptr = n;
                while (!ptr.equals(root)) {
                    moves.push(ptr.lastMove());
                    ptr = ptr.lastPuzzle();
                }
                return;
            }
        }
    }
}
