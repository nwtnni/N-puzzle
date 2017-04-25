package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 * A sliding puzzle implemented using arrays.
 */
public class ArrayPuzzle extends AbstractPuzzle {

    private int[][] grid;
    private Point[] location;
    private Point blank;

    /*
     * Creates a new m x n sliding-tile puzzle
     * in the solved configuration.
     *
     * Requires m, n > 1.
     */
    public ArrayPuzzle(int m, int n) {

        // Initialize fields
        this.n = n;
        this.m = m;
        grid = new int[m][n];
        location = new Point[n * m - 1];
        blank = new Point(n - 1, m - 1);
        grid[blank.y][blank.x] = -1;

        // Initialize grid
        for (int y = 0, i = 0; y < m; y++) {
            for (int x = 0; x < n && i < n * m - 1; x++) {
                grid[y][x] = i;
                location[i++] = new Point(x, y);
            }
        }
    }

    @Override
    public void randomize() {
        Random r = new Random();
        final int MAX_ITERATIONS = 1000;
        randomize(r.nextInt(MAX_ITERATIONS));
    }

    @Override
    public void randomize(int moves) {

        Random r = new Random();

        for (int i = 0; i < moves; i++) {
            List<Integer> next = validMoves();
            move(next.get(r.nextInt(next.size())));
        }
    }

    @Override
    public boolean move(int i) {

        // Invalid tile
        if (i < 0 || i > n * m - 2) {
            return false;
        }

        if (location[i].nextTo(blank)) {
            // Swap grid items
            grid[blank.y][blank.x] = i;
            grid[location[i].y][location[i].x] = -1;

            // Swap lookups
            Point tmp = blank;
            blank = location[i];
            location[i] = tmp;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Integer> validMoves() {
        ArrayList<Integer> moves = new ArrayList<Integer>();

        if (0 < blank.y) {
            moves.add(grid[blank.y - 1][blank.x]);
        } 
        if (blank.y < m - 1) {
            moves.add(grid[blank.y + 1][blank.x]);
        }
        if (0 < blank.x) {
            moves.add(grid[blank.y][blank.x - 1]);
        } 
        if (blank.x < n - 1) {
            moves.add(grid[blank.y][blank.x + 1]);
        }

        return moves;
    }

    @Override
    public Integer get(Point p) {
        if (0 <= p.y && p.y < m && 0 <= p.x && p.x < n) {
            return grid[p.y][p.x];
        }
        return null;
    }

    @Override
    public boolean solved() {
        for (int y = 0, i = 0; y < m; y++) {
            for (int x = 0; x < n && i < n * m - 1; x++) {
                if (grid[y][x] != i++) {
                    return false; 
                }
            }
        }
        return true;
    }

    public ArrayPuzzle copy() {
        ArrayPuzzle ap = new ArrayPuzzle(m, n);
        for (int y = 0; y < m; y++) {
            ap.grid[y] = Arrays.copyOf(grid[y], n);
        }
        return ap;
    }

    // Only checks that grid values are the same
    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayPuzzle) {
            ArrayPuzzle ap = (ArrayPuzzle) o;
            if (ap.m != m || ap.n != n) {
                return false; 
            } else {
                for (int y = 0; y < m; y++) {
                    for (int x = 0; x < n; x++)  {
                        if (ap.grid[y][x] != grid[y][x]) {
                            return false; 
                        }
                    }
                }
                return true;
            }
        } else {
            return false;  
        }
    }

    @Override 
    public int hashCode() {
        int hash = 0;
        for (int x = 0; x < n; x++) {
            hash += grid[0][x];
        }
        return hash;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        // Horizontal border
        StringBuilder hborder = new StringBuilder();
        for (int i = 0; i < 6 * n + 1; i++) {
            hborder.append("-");
        }
        hborder.append("\n");

        // Horizontal spacer
        StringBuilder hspacer = new StringBuilder();
        for (int i = 0; i < n; i++) {
            hspacer.append("|     ");
        }
        hspacer.append("|\n");

        for (int y = 0; y < m; y++) {

            sb.append(hborder);
            sb.append(hspacer);

            for (int x = 0; x < n; x++) {
                if (grid[y][x] == -1) {
                    sb.append("|     ");
                } else if (grid[y][x] < 10) {
                    sb.append("|  " + grid[y][x] + "  ");
                } else if (grid[y][x] < 100) {
                    sb.append("|  " + grid[y][x] + " ");
                } else {
                    sb.append("| " + grid[y][x] + " ");
                }
            }

            sb.append("|\n");
            sb.append(hspacer);
        }

        sb.append(hborder);
        return sb.toString();
    }
}
