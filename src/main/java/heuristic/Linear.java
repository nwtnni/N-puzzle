package heuristic;

import java.util.ArrayList;
import java.util.List;

import util.PuzzleNode;

/*
 * Represents the Manhattan distance heuristic
 * with linear conflict.
 */
public class Linear extends Heuristic {

    private boolean opt;

    public Linear(boolean optimal) {
        opt = optimal;
    }

    @Override
    public int evaluate(PuzzleNode n) {

        int counter = 0;
        int[] size = n.size();

        ArrayList<Integer> C = new ArrayList<Integer>();
        
        for (int y = 0; y < size[0]; y++) {
            for (int x = 0; x < size[1]; x++) {
                int k = n.get(x, y);
                if (k == size[0] * size[1] - 1) continue;
                int dy = Math.abs(k / size[1] - y);
                counter += dy;
                if (dy == 0) C.add(k);
            }
            counter += 2 * conflict(C);
            C.clear();
        }

        for (int x = 0; x < size[1]; x++) {
            for (int y = 0; y < size[0]; y++) {
                int k = n.get(x, y);
                if (k == size[0] * size[1] - 1) continue;
                int dx = Math.abs(k % size[1] - x);
                counter += dx;
                if (dx == 0) C.add(k);
            }
            counter += 2 * conflict(C);
            C.clear();
        }

        return (opt) ? counter + n.depth() : counter;
    }

    private int conflict(List<Integer> line) {

        int sum = 0;
        for (int i = 0; i < line.size(); i++) {
            for (int j = i + 1; j < line.size(); j++) {
                if (line.get(i) > line.get(j)) sum++;
            }
        }
        return sum;
    }
}

