package pe;

import static pe.util.Utils.run;
import pe.util.Mod;

/**
 * <p>Hilbert's New Hotel</p>
 * <a href="https://projecteuler.net/problem=359">Problem 359</a>
 */
public class Pe359 {
    static final Mod M = new Mod(100000000);

    public static void main(String[] args) {
        run(Pe359::solve);
    }
    static long solve() {
        long sum = 0;
        for (long i = 0, twos = 1; i <= 27; ++i, twos *= 2) {
            for (long j = 0, threes = 1; j <= 12; ++j, threes *= 3) {
                long f = twos * threes;
                sum = M.add(sum, p(f, 71328803586048L / f));
            }
        }
        return sum;
    }
    static long w(long f, long r) {
        return (r + ((f == 1) ? 0 : (f / 2 * 2 -1))) / 2;
    }
    static long p(long f, long r) {
        long w = w(f, r);
        return M.add(pos(f, r, w), acc(w));
    }
    static long pos(long f, long r, long w) {
        long rw  = w * 2 + 1;

        if ((r + (f == 1 ? 1:0)) % 2 == 0)
            return ((f % 2 == 1) ? (rw - f) / 2 : (rw + f) / 2) + rw + 1; 
        else
            return (f % 2 == 0)  ? (rw - f) / 2 + 1 : (rw + f) / 2;
    }
    static long acc(long w) {
        return M.multiply(w - 1, 4 + (w - 1) * 2) + 1;
    }
}
