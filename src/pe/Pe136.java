package pe;

import java.util.BitSet;

import static pe.util.Utils.run;

/**
 * <p>Singleton difference</p>
 * <a href="https://projecteuler.net/problem=136">Problem 136</a>
 */
public class Pe136 {
    private static final int    L          = 50_000_000;
    private static final BitSet EXISTS     = new BitSet(L + 1);
    private static final BitSet DUPLICATES = new BitSet(L + 1);

    public static void main(String[] args) {
        run(Pe136::solve);
    }
    static long solve() {
        for (int x = 1; x <= L; ++x) {
            for (int k = 4 - x % 4; k <= L / x && k < 3 * x; k += 4) {
                int n = x * k;
                if (EXISTS.get(n)) DUPLICATES.set(n);
                else EXISTS.set(n);
            }
        }
        int count = 0;
        for (int i = 1; i < L; ++i) {
            if (EXISTS.get(i) && !DUPLICATES.get(i)) ++count;
        }
        return count;
    }
}
