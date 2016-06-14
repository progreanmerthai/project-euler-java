package pe;

import pe.util.Mod;
import pe.util.Utils;

/**
 * <p>Sum of squares of unitary divisors</p>
 * <a href="https://projecteuler.net/problem=429">Problem 429</a>
 */
public class Pe429 {
    static final int L = 100_000_000;
    static final Mod M = new Mod(1_000_000_009);

    public static void main(String[] args) {
        Utils.run(Pe429::solve);
    }
    static long solve() {
        int[] ints = new int[L + 1];
        for (int i = 1; i <= L; ++i) ints[i] = i;
        long prod = 1;
        for (int i = 1; i <= L; ++i) if (ints[i] != 1) {
            int e = 0;
            for (int j = i; j <= L; j += i) {
                while (ints[j] % i == 0) {
                    ++e;
                    ints[j] /= i;
                };
            }
            prod = M.multiply(M.pow(i, e * 2) + 1, prod);
        }
        return prod;
    }
}
