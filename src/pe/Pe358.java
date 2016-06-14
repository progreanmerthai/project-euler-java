package pe;

import static pe.util.Utils.run;

import java.math.BigInteger;

/**
 * <p>Cyclic numbers</p>
 * <a href="https://projecteuler.net/problem=358">Problem 358</a>
 */
public class Pe358 {
    static final BigInteger RANGE_MIN = BigInteger.valueOf(724637681);
    static final BigInteger RANGE_MAX = BigInteger.valueOf(729927007);

    static final int LOWER_5 = 9891; // ???56789 * 09891 = ???99999
    public static void main(String[] args) {
        run(Pe358::solve);
    }
    static long solve() {
        for (BigInteger p = RANGE_MIN.nextProbablePrime();
                p.compareTo(RANGE_MAX) < 0; p = p.nextProbablePrime()) {
            int prime = p.intValue();
            if (prime % 100000 == LOWER_5) {
                long result = sumDigitsIfCyclic(prime);
                if (result != -1) return result;
            }
        }
        throw new AssertionError();
    }
    static long sumDigitsIfCyclic(int p) {
        long rem = 10;
        long sum = 0L;
        for (int i = 1; i < p; ++i) {
            sum += rem / p;
            long r = rem % p;
            if (r == 1 && i < p - 1) return -1;
            rem = r * 10;
        }
        return sum;
    }
}
