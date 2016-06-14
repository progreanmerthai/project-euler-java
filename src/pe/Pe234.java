package pe;

import static pe.util.Utils.run;

import java.util.BitSet;

/**
 * <p>Semidivisible numbers</p>
 * <a href="https://projecteuler.net/problem=234">Problem 234</a>
 */
public class Pe234 {
    static final int L = (int)Math.sqrt(999966663333L);

    public static void main(String[] args) {
        run(Pe234::solve);
    }
    static long solve() {
        BitSet bits = new BitSet();
        for (int i = 3; i <= L; i += 2) bits.set(i);

        long prev  = 2;
        long total = 0L;
        for (long cur = 3; cur >= 0; cur = bits.nextSetBit((int)cur + 1)) {
            for (long j = 3 * cur; j <= L; j += 2 *cur) bits.set((int)j, false);

            long range = (cur + prev) * (cur - prev);  
            total += sum(prev, range) + sum(-cur, range) - 2 * prev * cur;

            prev = cur;
        }
        return total;
    }
    static long sum(long diff, long range) {
        long b1 = Math.abs(range / diff);
        return diff * (2 * diff + b1 + 1) * b1 / 2;
    }
}