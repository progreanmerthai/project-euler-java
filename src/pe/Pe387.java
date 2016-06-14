package pe;

import static pe.util.Utils.run;

import java.math.BigInteger;
import java.util.stream.LongStream;

/**
 * <p>Harshad Numbers</p>
 * <a href="https://projecteuler.net/problem=387">Problem 387</a>
 */
public class Pe387 {
    public static void main(String[] args) {
        run(Pe387::solve);
    }
    public static long solve() {
        return LongStream.range(1, 10)
                .map(leftMost -> inEachLevel(leftMost, 14))
                .sum();
    }
    private static long inEachLevel(long number, int level) {
        return  isSRTHPrime(number) ? number :
                (level == 1 || !isRightTruncatable(number)) ? 0 :
                LongStream.range(0, 10)
                    .map(rightMost -> inEachLevel(number * 10 + rightMost, level - 1))
                    .sum();
    }
    public static boolean isSRTHPrime(long prime) {
        return prime > 10 && maybePrime(prime) && isStrongRightTruncatable(prime / 10);
    }
    public static boolean isStrongRightTruncatable(long number) {
        return isStrong(number) && isRightTruncatable(number);
    }
    private static boolean isStrong(long number) {
        return isHarshad(number) && maybePrime(number / digitsSum(number));
    }
    private static boolean isRightTruncatable(long number) {
        while (number > 0 && isHarshad(number)) number /= 10; 
        return number == 0;
    }
    public static boolean isHarshad(long number) {
        return number % digitsSum(number) == 0;
    }
    private static long digitsSum(long number) {
        long result = 0;
        while (number > 0) {
            result += number % 10;
            number /= 10;
        }
        return result;
    }
    private static boolean maybePrime(long number) {
        return BigInteger.valueOf(number).isProbablePrime(10);
    }
}
