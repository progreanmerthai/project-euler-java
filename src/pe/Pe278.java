package pe;

import static pe.util.Utils.run;
import pe.util.Primes;

/**
 * <p>Linear Combinations of Semiprimes
</p>
 * <a href="https://projecteuler.net/problem=278">Problem 278</a>
 */
public class Pe278 {
    public static void main(String[] args) {
        run(Pe278::solve);
    }
    private static long solve() {
        int[] primes = Primes.primeArray(5000);
        long sum = 0L;
        for (int ri = 2; ri < primes.length; ++ri) {
            for (int qi = 1; qi < ri; ++qi) {
                for (int pi = 0; pi < qi; ++pi) {
                    long p = primes[pi];
                    long q = primes[qi];
                    long r = primes[ri];
                    sum += 2*p*q*r - (p*q + p*r + q*r);
                }
            }            
        }
        return sum;
    }
}