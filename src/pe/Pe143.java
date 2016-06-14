package pe;

import static pe.util.Utils.run;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>Investigating the Torricelli point of a triangle</p>
 * <a href="https://projecteuler.net/problem=143">Problem 143</a>
 */
public class Pe143 {
    static final int L = 120000;
    public static void main(String[] args) {
        run(Pe143::solve);
    }
    private static int solve() {
        Set<Integer> set = new HashSet<>();
        for (long p = 1; p < L / 3; ++p) {
            for (long q = p; q < (L - p) /2; ++q) {
                if (isSquare(p*p + p*q + q*q)) {
                    for (long r = q; r < L - p - q; ++r) {
                        if (isSquare(q*q + q*r + r*r) && isSquare(p*p + p*r + r*r)) {
                            set.add((int)(p + q + r));
                        }                        
                    }
                }
            }
        }
        return set.stream().mapToInt(i->i).sum();
    }
    //TODO 重複
    private static boolean isSquare(long n) {
        long sqrt = (long) Math.sqrt(n);
        return sqrt * sqrt == n;
    }
}