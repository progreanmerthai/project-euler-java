package pe;

import static pe.util.Utils.run;

/**
 * <p>Panaitopol Primes</p>
 * <a href="https://projecteuler.net/problem=291">Problem 291</a>
 */
public class Pe291 {
    static final int L = (int) (Math.sqrt(2 * 5000000000000000L - 1)-1) / 2;
    static final long T[] = new long[L + 1]; 
    static {
        for (int n = 2; n <= L; ++n) T[n] = centeredSquareOf(n);
    }
    public static void main(String[] args) {
        run(Pe291::solve);
    }
    static long centeredSquareOf(long n) {
        return 2*n*n - 2*n + 1;
    }
    private static long solve() {
        long count = 0L;
        for (int i = 2; i <= L; ++i) {
            long f = T[i];
            if (f == 1) continue;
            if (f == centeredSquareOf(i)) ++count;
            for (long j = f; j + 1 - i <= L; j += f) {
                removeFactor((int)j + i, f);
                removeFactor((int)j + 1 - i, f);
            }
        }
        return count;
    }
    private static void removeFactor(int i, long f) {
        if (i <= L) while (T[i] % f == 0) T[i] /= f;
    }
}