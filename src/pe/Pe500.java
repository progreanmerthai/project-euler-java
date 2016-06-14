package pe;

import static pe.util.Utils.run;

import java.util.PriorityQueue;
import java.util.Queue;

import pe.util.Mod;
import pe.util.Primes;

/**
 * <p>Problem 500!!!</p>
 * <a href="https://projecteuler.net/problem=500">Problem 500</a>
 */
public class Pe500 {
    static final int E = 500500;
    static final Mod M = new Mod(500500507);

    public static void main(String[] args) {
        run(Pe500::solve);
    }
    private static long solve() {
        final Queue<Long> q = new PriorityQueue<>();
        Primes.stream(10_000_000).forEach(p -> q.add(Long.valueOf(p)));
        long prod = 1;
        for (int i = 0; i < E; ++i) {
            long n = q.remove();
            prod = M.multiply(n, prod);
            q.add(n * n);
        }
        return prod;
    }
}
