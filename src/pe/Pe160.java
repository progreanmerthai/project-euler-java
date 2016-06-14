package pe;

import static pe.util.Utils.run;
import pe.util.Mod;

/**
 * <p>Factorial trailing digits</p>
 * <a href="https://projecteuler.net/problem=160">Problem 160</a>
 */
public class Pe160 {
    static final int   T5 = 100000;
    static final int[] S  = new int[100001];
    static final long  N  = 1_000_000_000_000L;
    static final Mod   M  = new Mod(T5);

    public static void main(String[] args) {
        run(Pe160::solve);
    }
    static long solve() {
        prepare();
        return calc();
    }
    static void prepare() {
        S[0] = 1;
        for (int i = 0; i < T5; i += 5) {
            for (int j = 1; j <= 4; ++j) {
                S[i + j] = (int)M.multiply(S[i + j - 1], i + j);
            }
            S[i + 5] = S[i + 4] / 2;
        }        
    }
    static long calc() {
        long result = 1;
        for (long p = N; p > 1; p /= 5) {
            if (p % T5 > 0)
                result = M.multiply(result, S[(int) p % T5]);
            result = M.multiply(result, M.pow(S[T5], (int) (p / T5)));
        }
        return result;
    }
}