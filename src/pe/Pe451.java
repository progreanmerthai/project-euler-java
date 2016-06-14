package pe;

import static pe.util.Utils.run;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * <p>Modular inverses</p>
 * <a href="https://projecteuler.net/problem=451">Problem 451</a>
 */
public class Pe451 {
    static final int   L  = 20_000_000;
    static final int[] Is = new int[L + 1];

    public static void main(String[] args) {
        run(Pe451::solve);
    }
    static long solve() {
        Arrays.fill(Is, 1);
        for (int n = 1; n <= (L - 4) / 4; n++) f(n + 2, n + n + 3);
        return IntStream.range(3, L + 1).mapToLong(n -> Is[n]).sum();
    }
    static void f(int prevN, long i) {
        int n = (int) ((i * i - 1) / prevN);
        if (n > L || Is[n] == i) return;
        
        if (Is[n] < i) Is[n] = (int) i;
        f(n, n + n - i);
        f(n, i + n);
    }
}