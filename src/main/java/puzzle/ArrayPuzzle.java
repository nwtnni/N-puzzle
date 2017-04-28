package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 * A sliding puzzle implemented using arrays.
 */
public class ArrayPuzzle extends AbstractPuzzle {

    private int[] grid;
    private int blank;
    private int size;

    public ArrayPuzzle(int m, int n) {
        this.m = m;
        this.n = n;
        size = m * n;
        grid = new int[size];
        for (int i = 0; i < size; i++) {
            grid[i] = i;
        }
        blank = size - 1;
    }

    public ArrayPuzzle(int m, int n, int[] grid, int blank) {
        this.m = m;
        this.n = n;
        this.size = m * n;
        this.grid = grid;
        this.blank = blank;
    }

    @Override
    public int get(int x, int y) {
        return grid[y * n + x];
    }

    @Override
    public int[] size() {
        return new int[] {m, n};
    }

    @Override
    public void randomize(int moves) {
        Random r = new Random();
        for (int i = 0; i < moves; i++) {
            List<Move> next = validMoves();
            inPlaceMove(next.get(r.nextInt(next.size())));
        } 
    }

    @Override
    public boolean inPlaceMove(Move mv) {
        try {
            int d = delta(mv);
            grid[blank] = grid[blank + d];
            grid[blank + d] = size - 1;
            blank += d;
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false; 
        }
    }

    @Override
    public ArrayPuzzle move(Move mv) {
        int[] newGrid = Arrays.copyOf(grid, grid.length);
        int newBlank = blank;
        try {
            int d = delta(mv);
            newGrid[blank] = newGrid[blank + d];
            newGrid[blank + d] = size - 1;
            newBlank = blank + d;
            return new ArrayPuzzle(m, n, newGrid, newBlank);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /*
     * Private helper method for decoding move instructions.
     */
    private int delta(Move mv) {
        switch (mv) {
            case D:
                return -n;
            case U: 
                return +n;
            case L:
                return 1;
            case R:
                return -1;
            default:
                return 0;
        } 
    }

    @Override
    public List<Move> validMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        if (blank < (m - 1) * n) moves.add(Move.U);
        if (blank >= n) moves.add(Move.D);
        if (blank % n > 0) moves.add(Move.R);
        if (blank % n < n - 1) moves.add(Move.L);
        return moves; 
    }

    @Override
    public boolean solved() {
        for (int i = 0; i < m * n; i++) {
            if (grid[i] != i) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArrayPuzzle)) {
            return false;
        }

        ArrayPuzzle ap = (ArrayPuzzle) o;

        if (ap.m != m || ap.n != n) {
            return false; 
        }

        for (int i = 0; i < m * n; i++) {
            if (grid[i] != ap.grid[i]) {
                return false; 
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int sum = 0;
        for (int i = 0; i < m * n; i++) {
            sum += grid[i] * i;
        }
        return sum;
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

        for (int i = 0; i < m * n; i++) {

            if (i % n == 0) {
                sb.append(hborder);
                sb.append(hspacer);
            }

            if (grid[i] == m * n - 1) {
                sb.append("|     ");
            } else if (grid[i] < 10) {
                sb.append("|  " + grid[i] + "  ");
            } else if (grid[i] < 100) {
                sb.append("|  " + grid[i] + " ");
            } else {
                sb.append("| " + grid[i] + " ");
            }

            if (i % n == n - 1) {
                sb.append("|\n");
                sb.append(hspacer);
            }
        }

        sb.append(hborder);
        return sb.toString();
    }

}
