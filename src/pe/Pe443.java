package pe;

import static pe.util.Utils.run;
import pe.util.Primes;

/**
 * <p>GCD sequence</p>
 * <a href="https://projecteuler.net/problem=443">Problem 443</a>
 */
public class Pe443 {
    static final long  L  = 1000_000_000_000_000L;
    static final int[] PS = Primes.primeArray(10_000_000);

    public static void main(String[] args) {
        run(Pe443::solve);
    }
    static long solve() {
        long n = 9;
        long g = 27;
        long d = 8;
        while (n + d < L) {
            n += d;
            g += 3 * d;
            d = minPrimeDivisor(g - n - 1) / 2;
        }
        return g + (L - n);
    }
    static long minPrimeDivisor(long n) {
        for (int i = 0; i < PS.length; ++i) 
            if (n % PS[i] == 0) return PS[i];
        return n;
    }
}