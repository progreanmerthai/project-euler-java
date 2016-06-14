package pe;

import static pe.util.Utils.run;
import pe.util.Mod;

/**
 * <p>A weird recurrence relation</p>
 * <a href="https://projecteuler.net/problem=463">Problem 463</a>
 */
public class Pe463 {
    static final long L = 450283905890997363L;//3^37; 
    static final long N = L / 4;
    static final Mod  M = new Mod(1_000_000_000);

    public static void main(String[] args) {
        run(Pe463::solve);
    }
    public static long solve() {
        long n = (long)(Math.log(N)/Math.log(2));
        long p = 1L << n;
        long m = N - (p - 1);
        long ans = f(m, p << 1, 1, 0);

        Mod M3 = new Mod(1_000_000_000L*3);
        long x = M3.pow(4, (int)n);
        long start = M3.multiply(x, 16)/3;
        long base = 12*((1L<<n)-1)+16;

        return M.sum(start, ans, M.multiply(base, m));
    }
    static long f(long m, long p, long d2, long aug) {
        if (m < d2) return aug;
        long d1 = d2 * 4;
        long q1 = m / d1;
        long r1 = m % d1;
        long q2 = r1 / d2;
        long r2 = r1 % d2;
        
        long d2q1 = M.multiply(d2, q1);
        long tmp2 = 2 * (d2q1 + (q2 == 1 ? r2: (q2 > 1 ? d2: 0)));
        long tmp1 = 1 * (d2q1 + (q2 == 2 ? r2: (q2 > 2 ? d2: 0)));
        long tmp3 = 3 * (d2q1 + (q2 == 3 ? r2: 0));

        long tmp = M.multiply(p, tmp2 + tmp1 + tmp3);

        return f(m, p / 4, d2*4, tmp + aug);
    }
}