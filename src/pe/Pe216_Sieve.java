package pe;

import static pe.util.Utils.run;

/**
 * <p>Investigating the primality of numbers of the form 2n^2-1</p>
 * <a href="https://projecteuler.net/problem=216">Problem 216</a><br/>
 * (using sieve)
 */
public class Pe216_Sieve {
    static final int    L = 50_000_000;
    static final long[] T = new long[L + 1];
    static { for (int n = 2; n <= L; ++n) T[n] = t(n); }
    public static void main(String[] args) {
        run(Pe216_Sieve::solve);
    }
    static long solve() {
        int count = 0;
        for (int n = 2; n <= L; ++n) if (T[n] != 1) {
            if (T[n] == t(n)) ++count;
            for (long k = 1; k * T[n] - n <= L; ++k) {
                update(k * T[n] - n, n, T[n]);
                update(k * T[n] + n, n, T[n]);
            }
        }
        return count;
    }
    static void update(long m, long n, long p) {
        if (m > n && m <= L && T[(int)m] % p == 0) T[(int)m] /= p;
    }
    static long t(long n) {
        return 2L * n * n - 1;
    }
}
