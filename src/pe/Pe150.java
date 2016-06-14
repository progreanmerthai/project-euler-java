package pe;

import static pe.util.Utils.run;

/**
 * <p>Searching a triangular array for a sub-triangle having minimum-sum</p>
 * <a href="https://projecteuler.net/problem=150">Problem 150</a>
 */
public class Pe150 {
    private static final int SIZE = 1000;

    private static final int[][] START = new int[SIZE][];
    private static final int[][] DUMMY = new int[SIZE + 1][];

    public static void main(String[] args) {
        run(Pe150::solve);
    }
    static int solve() {
        init();
        return next(START, DUMMY, DUMMY, Integer.MAX_VALUE);
    }
    static void init() {
        long t   = 0L;
        for (int row = 0; row < SIZE; ++row) {
            START[row] = new int[row + 1];
            for (int col = 0; col < row + 1; ++col) {
                t = (615949 * t + 797807) % (1 << 20);
                START[row][col] = (int)(t - (1 << 19));
            }
        }
        for (int row = 0; row < SIZE + 1; ++row) DUMMY[row] = new int[row + 1];
    }
    static int next(int[][] t2, int[][] t1, int[][] t0, int max) {
        int[][] t3 = new int[t2.length - 1][];
        for (int r = 0; r < t3.length; ++r) {
            t3[r] = new int[t2[r].length];
            for (int c = 0; c < t2[r].length; ++c) {
                t3[r][c] = t2[r  ][c  ] + t2[r+1][c  ] + t2[r+1][c+1]
                         - t1[r+1][c  ] - t1[r+1][c+1] - t1[r+2][c+1]
                         + t0[r+2][c+1];
                max = Math.min(max, t3[r][c]);
            }
        }
        if (t3.length > 1) max = Math.min(max, next(t3, t2, t1, max));
        return max;
    }
}