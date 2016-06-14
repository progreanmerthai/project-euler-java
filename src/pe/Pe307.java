package pe;

import static pe.util.Utils.run;
import static pe.util.DoubleUtils.round;

import java.util.stream.Stream;

/**
 * <p>Chip Defects</p>
 * <a href="https://projecteuler.net/problem=307">Problem 307</a>
 */
public class Pe307 {
    static final double N = 1_000_000.0;
    static final int    K = 20_000;

    static class Probs {
        final int      dist;
        final double[] ps;
        Probs(int dist, double[] ps) {
            this.dist = dist;
            this.ps   = ps;
        }
        Probs(int dist, double init) {
            this(dist, new double[dist/ 2 + 2]);
            this.ps[0] = init;
        }
        double at(int i) { return this.ps[i]; } 
        void add(int i, double d) { if (d != 0) this.ps[i] += d / N; } 
        Probs next() {
            Probs next = new Probs(dist + 1, ps[0]);
            for (int i = 1; i < ps.length; ++i) {
                int pair   = i - 1;
                int single = dist - 2 * pair;
                next.add(i,     at(i) * (N - pair - single));
                next.add(i + 1, at(i) * single);
                next.add(0,     at(i) * pair);
            }
            return next;
        }
    }
    public static void main(String[] args) {
        run(() -> round(
                Stream.iterate(new Probs(1, new double[] {0.0, 1.0}), Probs::next)
                .skip(K - 1).findFirst().get().ps[0], 
          10));
    }
}