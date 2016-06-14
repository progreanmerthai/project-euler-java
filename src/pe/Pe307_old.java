package pe;

import static pe.util.Utils.run;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import pe.util.IntTuple;

/**
 * <p>Chip Defects</p>
 * <a href="https://projecteuler.net/problem=307">Problem 307</a>
 * (rewritten to a simpler and faster implementation)
 */
public class Pe307_old {
    static class Case extends IntTuple {
        Case(int one, int two) { super(one, two); }
        int singles() { return fst; }
        int pairs()   { return snd; }
        int sum()     { return fst + snd; }
        boolean hasSingles() { return fst > 0; } 
        boolean hasPairs()   { return snd > 0; } 
        Case incSingles() { return new Case(fst + 1, snd);     } 
        Case incPairs()   { return new Case(fst - 1, snd + 1); } 
    }
    static final double N = 1_000_000.0;
    static final int    K = 20_000;
    static final Map<Case, Double> PROBS = new HashMap<>();
    static {
        PROBS.put(new Case(1, 0), 1.0);
    }
    public static void main(String[] args) {
        run(Pe307_old::solve);
    }
    static double solve() {
        return IntStream.range(1, K).mapToDouble($ -> nextDefect()).sum();
    }
    private static double nextDefect() {
        Set<Map.Entry<Case, Double>> copy = new HashSet<>(PROBS.entrySet());
        PROBS.clear();
        return copy.stream().mapToDouble(e->update(e.getKey(), e.getValue() / N)).sum();
    }
    private static double update(Case c, double d) {
        put(c.incSingles(), d * (N - c.sum()));
        put(c.incPairs(),   d * c.singles());

        return d * c.pairs();
    }
    private static void put(Case c, double d) {
        if (d != 0) PROBS.put(c, PROBS.containsKey(c) ? PROBS.get(c) + d: d);
    }
}