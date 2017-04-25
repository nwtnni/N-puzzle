package puzzle;

/*
 * Simple class representing an (x, y) point
 * on an integer-valued grid.
 */
public class Point {

    public int x;
    public int y;

    /*
     * Creates point (x, y).
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*
     * Checks if the given point is adjacent to this one.
     *
     * @return True if the point is exactly one unit away
     *      on the grid; otherwise false.
     */
    public boolean nextTo(Point p) {
        int dx = Math.abs(p.x - x);
        int dy = Math.abs(p.y - y);
        return (dx + dy == 1);
    }
}
