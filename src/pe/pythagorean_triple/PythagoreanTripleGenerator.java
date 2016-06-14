package pe.pythagorean_triple;

import static java.util.Collections.singleton;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public class PythagoreanTripleGenerator {
    @FunctionalInterface public static interface Consumer {
        void apply(long t, long u, long s);
    }
    final long limit;
    final long[] seed;

    public PythagoreanTripleGenerator(long limit, long[] seed) {
        this.limit = limit;
        this.seed  = seed;
    }
    public Stream<long[]> stream() {
        Stream.Builder<long[]> sb = Stream.builder();
        Queue<long[]> queue = new LinkedList<>(singleton(seed));

        while (!queue.isEmpty()) {
            long[] x = queue.remove();
            sb.accept(x);
            for (long[] next: nextThree(x[0], x[1], x[2]))
                    if (next[0] < limit) queue.add(next);
        }
        return sb.build();
    }
    private static long[][] nextThree(long a, long b, long c) {
        return new long[][] {
                next(a, b, -c), next(a, -b, c), next(a, -b, -c) };
    }
    private static long[] next(long x, long y, long z) {
        return new long[] {
                3 * x + (-2) * y + (-2) * z,
                2 * x + (-1) * y + (-2) * z,
                2 * x + (-2) * y + (-1) * z};
    }
}