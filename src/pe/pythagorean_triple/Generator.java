package pe.pythagorean_triple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class Generator {
    private final long limit;

    public Generator(long limit) {
        this.limit = limit;
    }

    public Map<Long, List<long[]>> generate(int a, int b, int c) {
        final Queue<long[]> queue = new LinkedList<>();
        queue.add(new long[] { a, b, c });
    
        final Map<Long, List<long[]>> m = new TreeMap<>();
        while (!queue.isEmpty()) {
            for (long[] next: nextThreeTriples(queue.remove())) {
                if (next[0] >= limit) continue;
                queue.add(next);
                add(m, next);
            }
        }
        return m;
    }
    private static void add(Map<Long, List<long[]>> m, long[] next) {
        if (!m.containsKey(next[0])) m.put(next[0], new ArrayList<>());
        m.get(next[0]).add(next);
    }

    private static long[][] nextThreeTriples(long[] cur) {
        long a = cur[0];
        long b = cur[1];
        long c = cur[2];
        return new long[][] {
                nextPtt(a, b, -c), nextPtt(a, -b, c), nextPtt(a, -b, -c) };
    }

    private static long[] nextPtt(long x, long y, long z) {
        return new long[] {
                3 * x + (-2) * y + (-2) * z,
                2 * x + (-1) * y + (-2) * z,
                2 * x + (-2) * y + (-1) * z};
    }
    
}