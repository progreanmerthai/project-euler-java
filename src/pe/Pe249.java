package pe;

import static pe.util.Utils.run;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import pe.util.Mod;
import pe.util.Primes;

/**
 * <p>Prime Subset Sums</p>
 * <a href="https://projecteuler.net/problem=249">Problem 249</a>
 */
public class Pe249 {
    static final int            L     = 5000;
    static final Mod            M     = new Mod(10_000_000_000_000_000L);
    static Map<Integer, long[]> MEMO  = new HashMap<>();

    static Set<Integer> ALL_PRIMES;
    static int[]        PRIMES;
    
    public static void main(String[] args) {
        run(() -> {
            prepare();
            return count(0, 0);
        });
    }
    static void prepare() {
        BitSet primeBits = Primes.bitset(L);

        PRIMES     = primeBits.stream().toArray();
        ALL_PRIMES = Primes.stream(primeBits.stream().sum())
                .mapToObj(n -> n).collect(Collectors.toSet());

        int size = 0;
        for (int p : PRIMES) {
            long[] cache = new long[size + 1];
            Arrays.fill(cache, -1);
            MEMO.put(p, cache);
            size += p;
        }
    }
    static long count(int primeIndex, int sum) {
        if (primeIndex == PRIMES.length) return ALL_PRIMES.contains(sum) ? 1: 0;

        int prime = PRIMES[primeIndex];
        if (MEMO.get(prime)[sum] > -1) return MEMO.get(prime)[sum];

        long used    = count(primeIndex + 1, sum + prime);
        long notUsed = count(primeIndex + 1, sum);

        return memoise(prime, sum, M.add(used, notUsed));
    }
    static long memoise(int prime, int sum, long value) {
        MEMO.get(prime)[sum] = value;
        return value;
    }
}