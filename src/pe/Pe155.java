package pe;

import static pe.util.Utils.run;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>Counting Capacitor Circuits</p>
 * <a href="https://projecteuler.net/problem=155">Problem 155</a>
 */
public class Pe155 {
    static final int N = 18;
    static final long MASK = (1L << 32) - 1;
    
    @SuppressWarnings("unchecked")
    static Set<Long>[] capacitances = new Set[N + 1];
    static {
        capacitances[1] = Collections.singleton(combine(1, 1));
    }
    public static void main(String[] args) {
        run(() -> {
                solve(N);
                for (int i = 1; i < N; ++i) capacitances[N].addAll(capacitances[i]);
                return capacitances[N].size();
            });
    }
    static void solve(int n) {
        capacitances[n] = new TreeSet<>();
        for (int j = n - 1; j >= (n + 1) / 2; --j) {
            if (capacitances[j] == null) solve(j);
            for (long ci: capacitances[n - j])
                for (long cj: capacitances[j])
                    connect(capacitances[n], ci, cj);
        }
    }
    static void connect(Set<Long> dst, Long c1, Long c2) {
        long n1 = c1 >> 32;
        long n2 = c2 >> 32;
        long d1 = c1 & MASK;
        long d2 = c2 & MASK;

        dst.add(inSerial(n1, d1, n2, d2));
        dst.add(inParallel(n1, d1, n2, d2));
    }
    static long combine(long n, long d) {
        long gcd = gcd(d, n);
        return ((n / gcd) << 32) + d / gcd;
    }
    static long inSerial(long n1, long d1, long n2, long d2) {
        return combine(n1 * n2, n1 * d2 + n2 * d1);
    }
    static long inParallel(long n1, long d1, long n2, long d2) {
        return combine(n1 * d2 + n2 * d1, d1 * d2);
    }
    //TODO duplicate
    static long gcd(long d, long n) {
        return (n == 0) ? d : gcd(n, d % n);
    }
}