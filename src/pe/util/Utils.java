package pe.util;

import java.util.BitSet;
import java.util.function.Supplier;
import java.util.stream.LongStream;

public class Utils {
    public static <T> void run(Supplier<T> f) {
        long start = System.currentTimeMillis();
        System.out.printf("Ans. %s (%,d ms)%n",
                String.valueOf(f.get()), System.currentTimeMillis() - start);
    }

    public static LongStream primes(int limit) {
        final BitSet bits  = new BitSet();
        final int    max   = (int)Math.sqrt(limit + 1);
    
        bits.flip(2, limit + 1);
        for (int i = bits.nextSetBit(1); i < max; i = bits.nextSetBit(i+1)) {
            for (int j = i + i; j <= limit; j += i) bits.set(j, false);
        }
        return bits.stream().mapToLong(p->p);
    }
    public static long pow(long r, int e) {
        if (e == 0) return 1;
        else if ((e & 1) == 1) return r * pow(r * r, e >> 1);
        else return pow(r * r, e >> 1);
    }
    public static void main(String[] args) {
        System.out.println(pow(2, 0));
        System.out.println(pow(2, 1));
        System.out.println(pow(2, 2));
        System.out.println(pow(2, 3));
        System.out.println(pow(2, 4));
        System.out.println(pow(2, 5));
        System.out.println(pow(2, 6));
    }
}
