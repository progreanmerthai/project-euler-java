package pe;

import static pe.util.Utils.run;

import pe.util.Mod;

/**
 * <p>Sum of squares of divisors</p>
 * <a href="https://projecteuler.net/problem=401">Problem 401</a>
 */
public class Pe401 {
    static long L   = 1_000_000_000_000_000L;
    static long MOD = 1_000_000_000L;
    static Mod  M   = new Mod(MOD);

    public static void main(String[] args) {
        run(() -> solve(L));
    }
    static long solve(long n) {
        long sqrt = (long)Math.pow(n, 0.5);
        long from = sum2(n);
        long sum  = 0;
        for (long i = 1; i <= sqrt; ++i) {
            long to = sum2(n / (i + 1));
            sum = M.sum(sum,
                        M.multiply(i * i, n / i),
                        (i * i != from - to) ? M.multiply(from - to, i) : 0);
            from = to;
        }
        return sum;
    }
    static long sum2(long n) {
        switch ((int)(n % 6)) {
        case 0: return sum2(n, 6, 1, 1);
        case 1: return sum2(n, 1, 2, 3);
        case 2: return sum2(n, 2, 3, 1);
        case 3: return sum2(n, 3, 2, 1);
        case 4: return sum2(n, 2, 1, 3);
        case 5: return sum2(n, 1, 6, 1);
        default : throw new AssertionError();
        }
    }
    static long sum2(long n, int a, int b, int c) {
        return M.product(f(n, a), f(n + 1, b), f(2 * n + 1, c));
    }
    static long f(long n, long d) {
        return n % (MOD * d) / d;
    }
}