package pe;

import static pe.util.Utils.pow;
import static pe.util.Utils.run;

/**
 * <p>An enormous factorial</p>
 * <a href="https://projecteuler.net/problem=288">Problem 288</a>
 */
public class Pe288 {
    private static final long P = 61;
    private static final long Q = pow(10, 7);
    private static final int  E = 10;
    private static final long M = pow(P, E);

    public static void main(String[] args) {
        run(Pe288::solve);
    }
    private static long solve() {
        long s   = 290797;
        long u   = 0;
        long m   = 1;
        long nfm = 0;
        for (int n = 1; n <= Q; ++n) {
            if (n <= E) {
                u += m;
                m *= P;
            }
            s   = s * s % 50515093;
            nfm = (nfm + (s % P * u)) % M;
        }
        return nfm;
    }
}
