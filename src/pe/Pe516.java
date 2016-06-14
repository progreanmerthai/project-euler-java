package pe;

import static pe.util.Utils.run;

import java.math.BigInteger;
import java.util.Map;
import java.util.TreeMap;
import pe.util.Mod;

/**
 * <p>5-smooth totients</p>
 * <a href="https://projecteuler.net/problem=516">Problem 516</a>
 */
public class Pe516 {
    static final long L = 1_000_000_000_000L;
    static final Mod  M = new Mod(4294967296L);

    public static void main(String[] args) {
        run(Pe516::solve);
    }
    static long solve() {
        final Map<Long, Boolean> m = new TreeMap<>();
        prepare(new long[] {2, 3, 5}, 0, 1, m);

        return sum(m.keySet().stream().mapToLong(n -> n).toArray(),
                 m.entrySet().stream()
                    .filter(Map.Entry::getValue)
                    .mapToLong(e -> e.getKey() + 1).toArray(),
                 3, 
                 1);
    }
    static void prepare(long[] ps, int i, long n, Map<Long, Boolean> result) {
        for (long m = n; m <= L; m *= ps[i]) {
            if (i + 1 < ps.length) prepare(ps, i + 1, m, result);
            else result.put(m, BigInteger.valueOf(m + 1).isProbablePrime(5));
        }
    }
    private static long sum(long[] hs, long[] ps, int i, long n) {
        if (i < ps.length) {
            return M.add(
                    sum(hs, ps, i + 1, n),
                    L / n >= ps[i] ? sum(hs, ps, i + 1, n * ps[i]): 0);
        } else {
            long sum = 0;
            for (int j = 0; j < hs.length && hs[j] <= L / n; ++j)
                sum = M.add(sum, hs[j] * n);
            return sum;
        }
    }
}