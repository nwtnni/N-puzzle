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
        switch (mv) {
            case D:
                grid[blank] = grid[blank - n];
                grid[blank - n] = size - 1;
                blank -= n;
                break;
            case U: 
                grid[blank] = grid[blank + n];
                grid[blank + n] = size - 1;
                blank += n;
                break;
            case L:
                grid[blank] = grid[blank + 1];
                grid[blank + 1] = size - 1;
                blank += 1;
                break;
            case R:
                grid[blank] = grid[blank - 1];
                grid[blank - 1] = size - 1;
                blank -= 1;
                break;
            default:
                return false;
        } 
        return true; 
    }

    @Override
    public ArrayPuzzle move(Move mv) {
        try {

            int[] newGrid = Arrays.copyOf(grid, grid.length);
            int newBlank;

            switch (mv) {
                case D:
                    newGrid[blank] = newGrid[blank - n];
                    newGrid[blank - n] = size - 1;
                    newBlank = blank - n;
                    break;
                case U: 
                    newGrid[blank] = newGrid[blank + n];
                    newGrid[blank + n] = size - 1;
                    newBlank = blank + n;
                    break;
                case L:
                    newGrid[blank] = newGrid[blank + 1];
                    newGrid[blank + 1] = size - 1;
                    newBlank = blank + 1;
                    break;
                case R:
                    newGrid[blank] = newGrid[blank - 1];
                    newGrid[blank - 1] = size - 1;
                    newBlank = blank - 1;
                    break;
                default:
                    return null;
            } 

            return new ArrayPuzzle(m, n, newGrid, newBlank);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Move> validMoves() {
        ArrayList<Move> moves = new ArrayList<Move>();
        if (blank < (m - 1) * n) {
            moves.add(Move.U);
        }
        if (blank >= n) {
            moves.add(Move.D);
        }
        if (blank % n > 0) {
            moves.add(Move.R);
        }
        if (blank % n < n - 1) {
            moves.add(Move.L);
        }
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
