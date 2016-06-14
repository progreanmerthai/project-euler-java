package pe;

import java.util.stream.IntStream;
import static pe.util.Utils.run;

/**
 * <p>Tribonacci non-divisors</p>
 * <a href="https://projecteuler.net/problem=225">Problem 225</a>
 */
public class Pe225 {
    public static void main(String[] args) {
        run(() -> IntStream.iterate(3, i -> i + 2)
                    .filter(Pe225::nonDivisor)
                    .skip(123)
                    .findFirst().getAsInt());
    }
    private static boolean nonDivisor(int n) {
        int t1 = 1, t2 = 1, t3 = 1;
        int t4;
        while (0 != (t4 = (t1 + (t2 + t3) % n) % n)) {
            if (t2 == 1 && t3 == 1 && t4 == 1) return true;
            t1 = t2; t2 = t3; t3 = t4;
        }
        return false;
    }
}
