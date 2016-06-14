package pe;

import static pe.util.Utils.run;

/**
 * <p>Investigating progressive numbers, n, which are also square</p>
 * <a href="https://projecteuler.net/problem=141">Problem 141</a>
 */
public class Pe141 {
    private static final long L = 1_000_000_000_000L;
    public static void main(String[] args) {
        run(Pe141::solve);
    }
    static long solve() {
        long sum = 0L;
        for (long a = 1; pn(a, a + 1, a) < L; ++a) {
            for (long b = a + 1; pn(a, b, 1) < L; ++b) if (isCoprime(b, a)) {
                for (long c = 1, n = pn(a, b, c); n < L; n = pn(a, b, ++c)) if (isSquare(n)) { 
                    sum += n;
                }
            }
        }
        return sum;
    }
    private static long pn(long a, long b, long c) {
        return a * b * b * b * c * c + a * a * c;
    }
    private static boolean isSquare(long n) {
        long r = (long)Math.sqrt(n);
        return r * r == n;
    }
    private static boolean isCoprime(long b, long a) {
        if (a == 1) return true;
        if (b == a) return false;
        long d = b - a;
        return (a < d) ? isCoprime(d, a) : isCoprime(a, d);
    }
}
