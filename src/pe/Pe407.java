package pe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import pe.util.Utils;

/**
 * <p>Idempotents</p>
 * <a href="https://projecteuler.net/problem=407">Problem 407</a>
 */
public class Pe407 {
    public static void main(String[] args) {
        Utils.run(() -> new Pe407(10_000_001).solve());
    }
    private final int limit;
    private final long minp[];
    public Pe407(int limit) {
        this.limit = limit;
        this.minp = new long[limit];
    }
    private long solve() {
        return IntStream.range(2, limit).mapToLong(this::forEachN).sum();
    }
    private long forEachN(int n) {
        if (minp[n] == 0) updateMinPrimeFactor(n);
        List<Integer> poweredPrimes = poweredPrimesOf(n);

        return IntStream.range(0, (1 << (poweredPrimes.size() - 1))).map(mask -> {
            int[] ms = createPair(poweredPrimes, mask);
            int a    = ms[0] * invert(ms[0], ms[1]);
            return (a == 1 || a > n / 2) ? a : n + 1 - a;
        }).max().getAsInt();
    }
    private int[] createPair(List<Integer> pw2, int mask) {
        int ms[] = new int[] {1, 1};
        for (int k = 0; k < pw2.size(); k++) {
            if ((mask & 1 << k) != 0) ms[0] *= pw2.get(k);
            else ms[1] *= pw2.get(k);
        }
        return ms;
    }
    private void updateMinPrimeFactor(int i) {
        for(int j = i; j < limit; j += i) if(minp[j] == 0) minp[j] = i;
    }
    private List<Integer> poweredPrimesOf(int n) {
        List<Integer> result = new ArrayList<>();
        for(int x = n; x > 1;) {
            int q = 1;
            long p = minp[x];
            for(; x % p == 0; x /= p) q *= p;
            result.add(q);
        }
        return result;
    }
    private static int invert(int a1, int a2) {
        int x2 = invert(a1, a2, 1, 0);
        return x2 < 0 ? x2 + a2 : x2;
    }
    private static int invert(int a1, int a2, int x1, int x2) {
        if (a2 == 1) return x2;
        int q = a1 / a2;
        return invert(a2, a1 - q * a2, x2, x1 - q * x2);
    }
}
