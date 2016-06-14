package pe.util;

import java.util.BitSet;
import java.util.stream.IntStream;

public class Primes {
    public static BitSet bitset(int limit) {
        final BitSet bits  = new BitSet();
        final int    max   = (int)Math.sqrt(limit + 1);
    
        bits.flip(2, limit + 1);
        for (int i = bits.nextSetBit(1); i <= max; i = bits.nextSetBit(i+1)) {
            for (int j = i + i; j <= limit; j += i) bits.set(j, false);
        }
        return bits;
    }
    public static IntStream stream(int limit) {
        return bitset(limit).stream();
    }
    public static int[] primeArray(int limit) {
        return stream(limit).toArray();
    }
}
