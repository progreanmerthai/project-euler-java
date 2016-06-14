package pe;

import java.util.stream.IntStream;
import static pe.util.Utils.run;

/**
 * <p>Double pandigital number divisible by 11</p>
 * <a href="https://projecteuler.net/problem=491">Problem 491</a>
 */
public class Pe491 {
    public static void main(String[] args) {
        run(() -> IntStream.of(23, 34, 45, 56, 67)
                 .mapToLong(sum -> count(sum, 0, 0, 0, 0)).sum());
    }
    public static long count(int sum, int bits, int d, int pos, int cur) {
        if (pos == 10) return (cur == sum) ? even(bits) * odd(reverse(bits)): 0;
        long c = 0;
        if (used(bits, d) < 2) c += count(sum, add(bits, d, 1), d, pos + 1, cur + d);
        if (d < 9)             c += count(sum, bits,            d + 1, pos, cur);
        return c;
    }
    public static long even(int bits) {
        return combination((10 - used(bits, 0)) * 362880, bits);
    }
    public static long odd(int bits) {
        return combination(3628800, bits);
    }
    public static long combination(int n, int bits) {
        for (int i = 0; i < 10; ++i) if (used(bits, i)==2) n /= 2;
        return n;
    }
    static int reverse(int bits) {
        int result = 0;
        for (int i = 0; i <= 9; ++i) result = add(result, i, 2 - used(bits, i));
        return result;
    }
    static int used(int bits, int d) {
        return (bits >> (d + d)) & 3;
    }
    static int add(int bits, int d, int v) {
        return bits + (v << (d + d));
    }
}
