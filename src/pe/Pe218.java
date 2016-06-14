package pe;

import pe.pythagorean_triple.PythagoreanTripleGenerator;
import pe.util.Mod;
import pe.util.Utils;                       

/**
 * <p>Perfect right-angled triangles</p>
 * <a href="https://projecteuler.net/problem=218">Problem 218</a>
 */
public class Pe218 {
    static final long   L     = 100_000_000;
    static final Mod    M     = new Mod(84);
    static final long[] SEED  = new long[] { 5, 4, 3 };

    public static void main(String[] args) {
        Utils.run(Pe218::solve);
    }
    static long solve() {
        return new PythagoreanTripleGenerator(L, SEED).stream()
                .filter(pt->!isSuperPerfect(pt[1], pt[2]))
                .count();
    }
    private static boolean isSuperPerfect(long s, long t) {
        return M.product(s, t, s-t, s+t) == 0;
    }
}