package pe;

import static pe.util.Utils.run;
import java.util.function.IntBinaryOperator;

/**
 * <p>Step Numbers</p>
 * <a href="https://projecteuler.net/problem=178">Problem 178</a>
 */
public class Pe178 {
    static final int MAX_COL   = 40;
    static final int DIGITS    = 10;
    static final int MAX_DIGIT = DIGITS - 1;

    static class Counter {
        final int min;
        final int max;
        final IntBinaryOperator terminal;
        final long cache[][] = new long [DIGITS][MAX_COL + 1];

        Counter(int min, int max) { this(min, max, (l, r) -> 1); }

        Counter(int min, int max, IntBinaryOperator terminal) {
            this.min      = min;
            this.max      = max;
            this.terminal = terminal;
        }
        public long count(int digit, int rest) {
            if (rest <= 1)              return terminal.applyAsInt(digit, max);
            if (cache[digit][rest] > 0) return cache[digit][rest];

            long sum = 0;
            if (digit < max) sum += count(digit + 1, rest - 1);
            if (digit > min) sum += count(digit - 1, rest - 1);
            cache[digit][rest] = sum;

            return sum;
        }
    }
    static Counter counterA = new Counter(1, MAX_DIGIT - 1);
    static Counter counterB = new Counter(0, MAX_DIGIT - 1, (d, max) -> (d == max) ? 1 : 0);
    static Counter counterC = new Counter(0, MAX_DIGIT);

    public static void main(String[] args) {
        run(Pe178::solve);
    }
    static private long solve() {
        long sum = 0L;
        for (int wA = 0; wA <= MAX_COL - DIGITS; ++wA) {
            long sumA = counterA.count(1, wA);
            for (int wB = DIGITS; wB <= MAX_COL - wA; wB += 2) {
                long sumB = counterB.count(1, wB - 2);
                for (int wC = 0; wC <= MAX_COL - wA - wB; ++wC) {
                    long sumC = counterC.count(MAX_DIGIT - 1, wC);
                    sum += sumA * sumB * sumC;
                    if (wA > 0) sum += sumA * sumB * sumC;
                }
            }
        }
        return sum;
    }
}