package pe;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import pe.util.Utils;

public class Pe215 {
    static final int H = 32;
    static final int V = 10;
    static final Set<Integer>                PATTERNS   = new HashSet<>();
    static final Map<Integer, List<Integer>> CRACK_FREE = new HashMap<>();
    static final Map<Integer, long[]>        MEMO       = new HashMap<>();

    public static void main(String[] args) {
        Utils.run(Pe215::solve);
    }
    
    static long solve() {
        enumeratePatterns(0, H);
        enumerateCrackFreePairs();
        return countUp();
    }
    static void enumeratePatterns(int brickPattern, int remaining) {
        if (remaining == 1) return;
        if (remaining == 2 || remaining == 3) {
            PATTERNS.add(brickPattern);
        } else {
            enumeratePatterns(addBrick(brickPattern, remaining, 2), remaining - 2);
            enumeratePatterns(addBrick(brickPattern, remaining, 3), remaining - 3);
        }
    }
    static void enumerateCrackFreePairs() {
        for (Integer layer: PATTERNS) CRACK_FREE.put(layer, new ArrayList<>());

        Deque<Integer> q = new ArrayDeque<>(PATTERNS);
        while (!q.isEmpty()) {
            Integer pattern1 = q.pop();
            for (Integer pattern2: q) {
                if ((pattern1.intValue() & pattern2.intValue()) == 0) {
                    CRACK_FREE.get(pattern1).add(pattern2);
                    CRACK_FREE.get(pattern2).add(pattern1);
                }
            }
        }
    }
    static int addBrick(int pattern, int position, int width) {
        return pattern + (1 << (H - position + width - 1));
    }
    private static long countUp() {
        for (Integer layer: PATTERNS) MEMO.put(layer, new long[V + 1]);
        return CRACK_FREE.keySet().stream()
                .map(p -> count(V, p))
                .mapToLong(n->n).sum();
    }
    private static long count(int layer, int pattern) {
        if (layer == 2)                   return CRACK_FREE.get(pattern).size();
        if (MEMO.get(pattern)[layer] > 0) return MEMO.get(pattern)[layer];

        MEMO.get(pattern)[layer] = CRACK_FREE.get(pattern).stream()
                .map(next -> count(layer - 1, next))
                .mapToLong(n->n).sum();

        return MEMO.get(pattern)[layer];
    }
}
