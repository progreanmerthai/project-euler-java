package pe;

import java.util.stream.IntStream;
import static pe.util.Utils.run;

/**
 * <p>Square on the Inside</p>
 * <a href="https://projecteuler.net/problem=504">Problem 504</a>
 */
public class Pe504 {
    static final int     M      = 100;
    static final int[][] POINTS = new int[M + 1][M + 1];

    public static void main(String[] args) {
        run(Pe504::solve);
    }
    static {
        for (int x = 2; x <= M; ++x) 
            for (int y = x; y <= M; ++y) 
                POINTS[x][y] = count(x, y);
    }
    private static long solve() {
        int[] cnt = new int[141 * 141];
        for (int a = 1; a <= M; ++a) {
            for (int b = 1; b <= M; ++b) {
                for (int c = 1; c <= M; ++c) {
                    for (int d = 1; d <= M; ++d) {
                        cnt[points(a, b) 
                          + points(b, c)
                          + points(c, d)
                          + points(d, a)
                          + (a + b + c + d - 3)]++;
                    }
                }                
            }
        }
        return IntStream.range(1, 141).map(n -> cnt[n * n]).sum();
    }
    private static int points(int n, int m) {
        return POINTS[Integer.min(n, m)][Integer.max(n, m)];
    }
    private static int count(int x, int y) {
        return IntStream.range(1, x).map(p -> points(p * y / (double)x)).sum();
    }
    static int points(double n) {
        return (int)((int)n == n ? (n - 1) : n);
    }
}