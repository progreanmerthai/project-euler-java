package pe;

import static pe.util.Utils.run;
import java.math.BigInteger;

/**
 * <p>Panaitopol Primes</p>
 * <a href="https://projecteuler.net/problem=291">Problem 291</a>
 * This naive implementation takes too much time (5 to 6 minutes).  
 */
public class Pe291_Old {
    public static void main(String[] args) {
        run(Pe291_Old::solve);
    }
    private static long solve() {
        long count = 0L;
        for (long n = 1, pp; (pp = n*n + (n+1)*(n+1)) < 5000000000000000L; ++n)
                if (BigInteger.valueOf(pp).isProbablePrime(5)) ++count;
        return count;
    }
}