package pe;

import static pe.util.Utils.run;

/**
 * <p>Same differences</p>
 * <a href="https://projecteuler.net/problem=135">Problem 135</a>
 */
public class Pe135 {
    private static final int   L  = 1_000_000;
    private static final int[] DP = new int[L + 1];

    public static void main(String[] args) {
        run(Pe135::solve);
    }
    private static long solve() {
        for (long d = 1; d <= L / 4; ++d) h(d);

        int count = 0;
        for (int i = 1; i < L; ++i) if (DP[i] == 10) count++;
        return count;
    }
    private static void h(long d) {
        for (long q = 1; q <= 2 * d; ++q) {
            int n = (int) (q * (2 * d * 2 - q));
            if (n >= L) break;
            DP[n] += (3 * d * d < n && 3 * d - q != -d + q) ? 2 : 1;
        }
    }
}
