package pe;

import static pe.util.Utils.run;
import static java.lang.Integer.max;

/**
 * <p>Digital root sums of factorisations</p>
 * <a href="https://projecteuler.net/problem=159">Problem 159</a>
 */
public class Pe159 {
    static final int M = 1000;
    static final int N = M * M;
    static final int[]   MINP = new int[N + 1];
    static final int[][] MEMO = new int[M + 1][];

    public static void main(String[] args) {
        run(() -> {
                prepareMemo();
                prepareMinPrime();
                return solve();
            });
    }
    static void prepareMemo() {
        for (int i = 2; i <= M; ++i) MEMO[i] = new int[N / i + 1];
    }
    static void prepareMinPrime() {
        for (int p = 2; p <= M; ++p) 
            if (MINP[p] == 0)
                for (int j = p * p; j <= N; j += p) 
                    if (MINP[j] == 0) MINP[j] = p;
    }
    static long solve() {
        long sum = 0;
        for (int i = 2; i < N; ++i) sum += mdrs(i);
        return sum;
    }
    static int drs(int n) {
        return (n - 1) % 9 + 1;
    }
    static int mdrs(int n) {
        int drs = drs(n);
        if (MINP[n] == 0) return drs;
        
        return selectMax(n, drs, MINP[n]);
    }
    static int subMdrs(int m, int n) {
        if (MEMO[m][n] != 0) return MEMO[m][n];

        int drs = drs(n);
        if (MINP[n] != 0) {
            drs = selectMax(n, drs, max(MINP[n], m));
        }
        return MEMO[m][n] = drs(m) + drs;
    }
    private static int selectMax(int n, int max, int start) {
        for (int i = start; i <= (int)Math.sqrt(n); ++i) if (n % i == 0) {
            max = max(subMdrs(i, n / i), max);
        }
        return max;
    }
}