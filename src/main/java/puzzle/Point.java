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

    public Point(Point p) {
        x = p.x;
        y = p.y;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point)) {
            return false; 
        }

        Point p = (Point) o;

        return (p.x == x && p.y == y);
    }

    @Override
    public int hashCode() {
        return x * y; 
    }
}
