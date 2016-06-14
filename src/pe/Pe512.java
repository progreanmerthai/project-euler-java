package pe;

import static pe.util.Utils.run;

/**
 * <p>Sums of totients of powers</p>
 * <a href="https://projecteuler.net/problem=512">Problem 512</a>
 */
public class Pe512 {
    static final int L = 500_000_000;
    static final int H = L / 2;
    static final int R = (int) Math.sqrt(L) / 2;
    public static void main(String[] args) {
        run(Pe512::solve);
    }
    static long solve() {
        final int[] fs = new int[H];
        final int[] ds = new int[H];
        long sum = 1;
        for (int p = 3, i = p/2; i < H; p += 2, i = p/2) {
            if (i <= R && fs[i] == 0) {
                for (int j = i; j < H; j += p) {
                    if (fs[j] == 0) {
                        fs[j] = p - 1;
                        ds[j] = (j * 2 + 1) / p;
                    } else {
                        fs[j] *= p - 1;
                        ds[j] /= p;
                    }
                    while (ds[j] % p == 0) {
                        fs[j] *= p;
                        ds[j] /= p;
                    }
                }
            }
            sum +=  fs[i] == 0 ? i * 2 :
                    ds[i] == 1 ? fs[i] :
                    fs[i] * (ds[i] - 1);
        }
        return sum;
    }
}