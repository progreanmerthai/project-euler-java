package pe;
import static pe.util.Utils.run;

/**
 * <p>Rectangles in cross-hatched grids</p>
 * <a href="https://projecteuler.net/problem=147">Problem 147</a>
 */
public class Pe147 {
    static final int COLS = 47;
    static final int ROWS = 43;

    public static void main(String[] args) {
        run(Pe147::solve);
    }
    private static long solve() {
        return countDiagonalRect(COLS, ROWS) + countStraightRect(COLS, ROWS);
    }
    private static long countDiagonalRect(int cols, int rows) {
        long total = 0;
        for (int width = 1; width <= cols; width++) {
            long prevCount = 0;
            long shapes = 0;
            for (int height = 1; height <= Integer.min(width, rows); height++) {
                shapes += f(width, height);
                long count = prevCount + shapes;
                total += count;
                if (height < width && width <= rows) total += count;
                prevCount = count;
            }
        }
        return total;
    }
    private static long countStraightRect(int cols, int rows) {
        return g(cols) * g(rows);
    }
    private static long f(int width, int height) {
        return height == 1  ? width - 1
                            : (height-1) * (width - height + 1) * 8 - 1;
    }
    private static long g(int n) {
        return (n * (n + 1) * (2 * n + 1) / 6 + n * (n + 1) / 2) / 2;
    }
}