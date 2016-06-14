package pe;

import pe.util.Primes;
import pe.util.Utils;

/**
 * <p>Sliding game</p>
 * <a href="https://projecteuler.net/problem=313">Problem 313</a>
 */
public class Pe313 {
    static int L = 1_000_000;
    public static void main(String[] args) {
        Utils.run(Pe313::solve);
    }
    private static long solve() {
        return 2 * (Primes.stream(L).skip(2).mapToLong(Pe313::countFor).sum() + 1);
    }
    static long countFor(long p) {
        return ((p * p + 5)/6 - 5)/ 4 + 1;
    }
}