package pe;

import static pe.util.Utils.run;

import java.util.BitSet;
import pe.util.Primes;

/**
 * <p>Prime triples and geometric sequences</p>
 * <a href="https://projecteuler.net/problem=518">Problem 518</a>
 */
public class Pe518 {
    static int R = 10000;
    static int L = R * R;
    public static void main(String[] args) {
        run(Pe518::solve);
    }
    private static long solve() {
        BitSet qs = new BitSet();
        Primes.stream(L).forEach(p -> qs.set(p + 1));
        BitSet rs = new BitSet();
        long sum = 0;
        for (int num = 2; num < R; num++) {
            for (int den = 1; den < num; ++den) {
                int index = num * R + den;
                if (rs.get(index)) continue;

                int da = den*den, db = num*den, dc = num*num;
                for (int a = da, b = db, c = dc; c <= L; a += da, b += db, c += dc) {
                    if (qs.get(c) && qs.get(b) && qs.get(a)) {
                        sum += a + b + c -3; 
                    }
                }
                for (int k = 2; k * num < R; ++k) {
                    rs.set(index * k);
                }
            }
        }
        return sum;
    }
}