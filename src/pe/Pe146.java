package pe;

import static pe.util.Utils.run;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * <p>Investigating a Prime Pattern</p>
 * <a href="https://projecteuler.net/problem=146">Problem 146</a>
 */
public class Pe146 {
    private static final int L = 150_000_000;
    private static final Set<Integer> ds =
            new TreeSet<>(Arrays.asList(1, 3, 7, 9, 13, 27));

    public static void main(String[] args) {
        run(Pe146::solve);
    }
    private static long solve() {
        return LongStream.iterate(10, n->n+10).limit(L / 10)
                        .filter(n-> n % 3 !=0 && n % 7 != 0 && n % 13 != 0)
                        .filter(n->matches(n, 1))
                        // above two filters are for better performance
                        .filter(n->matches(n, 15))
                        .sum();
    }
    private static boolean matches(long n, final int certainty) {
        return IntStream.iterate(1, i-> i+2).limit(14) // [1, 3, .. 27]
                .allMatch(d -> !ds.contains(d) ^ isPrime(n, d, certainty));
    }
    //TODO 重複
    private static boolean isPrime(long n, int d, int certainty) {
        return BigInteger.valueOf(n * n + d).isProbablePrime(certainty);
    }
}