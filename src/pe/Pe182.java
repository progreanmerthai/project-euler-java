package pe;

import static pe.util.Utils.run;

import java.util.BitSet;
import java.util.Set;
import java.util.TreeSet;

import pe.util.Primes;

/**
 * <p>RSA encryption</p>
 * <a href="https://projecteuler.net/problem=182">Problem 182</a>
 */
public class Pe182 {
    static final int PHI = (1009 - 1) * (3643 - 1);

    public static void main(String[] args) {
        run(() -> {
            BitSet es = new BitSet();
            for (int p: primeFactors(PHI)) {
                for (int i = p;         i < PHI; i += p)     es.set(i);
                for (int i = p + p + 1; i < PHI; i += p + p) es.set(i);
            }
            es.flip(2, PHI);
            return es.stream().mapToLong(e -> e).sum();
        });
    }
    private static Set<Integer> primeFactors(int n) {
        Set<Integer> set = new TreeSet<>();
        for (int p: Primes.primeArray(PHI)) {
            for ( ; n % p == 0; n /= p) set.add(p);
            if (n == 1) return set;
        }
        throw new AssertionError();
    }
}