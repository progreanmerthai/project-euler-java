package pe;

import static pe.util.Utils.run;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

/**
 * <p>Digital root clocks</p>
 * <a href="https://projecteuler.net/problem=315">Problem 315</a>
 */
public class Pe315 {
    public static abstract class RootClock {
        private static int[] PATTERNS = new int[] {
            0b01110111,//0: 012456
            0b00100100,//1: 25
            0b01011101,//2: 02346
            0b01101101,//3: 02356
            0b00101110,//4: 1235
            0b01101011,//5: 01356
            0b01111011,//6: 013456
            0b00100111,//7: 0125
            0b01111111,//8: 0123456
            0b01101111,//9: 012356
            };
        private int transition = 0;
        protected BitSet bits = new BitSet();
    
        public int transition() { return transition; }
        protected abstract void transit(int number);

        public void calc(int number) {
            transit(number);
            if (number / 10 > 0) calc(next(number));
            else transit(0);
        }
        private int next(int number) {
            return digitsOf(number).stream().mapToInt(Integer::intValue).sum();
        }
        private static List<Integer> digitsOf(int number) {
            List<Integer> digits = new ArrayList<Integer>();
            while (number > 0) {
                digits.add(number % 10);
                number /= 10;
            }
            return digits;
        }
        void incrementBy(BitSet xor) {
            for (int i = xor.nextSetBit(0); i >= 0; i = xor.nextSetBit(i + 1))
                    ++this.transition;
        }
        protected void update(int number) {
            List<Integer> digits = digitsOf(number);
            byte[] bytes = new byte[digits.size()];
            for (int i = 0; i < bytes.length; ++i) bytes[i] = (byte)PATTERNS[digits.get(i)];

            this.bits = BitSet.valueOf(bytes);
        }
    }
    public static void main(String[] args) {
        run(()->{
            final RootClock max = new MaxsClock();
            final SamsClock sam = new SamsClock();
            primes(20_000_000).filter(p->p > 10_000_000).forEach( p-> {
                sam.calc(p);
                max.calc(p);
            });
            return sam.transition() - max.transition();
        });
    }
    public static class SamsClock extends RootClock {
        @Override public void transit(int number) {
            incrementBy(this.bits);
            update(number);
            incrementBy(this.bits);
        }
    }
    public static class MaxsClock extends RootClock {
        @Override public void transit(int number) {
            BitSet xor = (BitSet)this.bits.clone();
            update(number);
    
            xor.xor(this.bits);
            incrementBy(xor);
        }
    }
    private static IntStream primes(final int limit) {
        BitSet bits = new BitSet(limit);
        bits.set(2, limit, true);
        int limitSqrt = (int)Math.sqrt(limit);

        for (int i = 2; i <= limitSqrt; ++i) {
            for (int j = i*2; j < limit; j += i) bits.set(j, false);
        }
        return bits.stream(); 
    }
}
