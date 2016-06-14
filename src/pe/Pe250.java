package pe;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.valueOf;
import static pe.util.Utils.run;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import pe.util.Mod;
import pe.util.Primes;

/**
 * <p>250250</p>
 * <a href="https://projecteuler.net/problem=250">Problem 250</a>
 */
public class Pe250 {
    static final int        LIMIT = 250250;
    static final int        M1    = 250;
    static final BigInteger M2    = valueOf(10_000_000_000_000_000L);

    static final Mod                        MOD        = new Mod(M1);
    static final Map<Integer, BigInteger[]> COMBs      = new HashMap<>();
    static final BitSet                     PRIME_BITs = Primes.bitset(LIMIT); 
    static final int []                     COUNTs     = new int[M1];
    static int[]          POWMODs;
    static BigInteger[][] MEMO;
    
    public static void main(String[] args) {
        run(Pe250::solve);
    }
    static long solve() {
        prepare();
        BigInteger path0 = valueOf(2).modPow(valueOf(COUNTs[0]), M2);
        BigInteger pathN = paths(1, 0);

        return path0.multiply(pathN).mod(M2).longValue() - 1;
    }
    static void prepare() {
        IntStream.rangeClosed(1, LIMIT)
                .forEach(i -> COUNTs[(int) MOD.pow(i, i)]++);
        POWMODs = IntStream.range(0, M1)
                .filter(n -> COUNTs[n] != 0).toArray();
        IntStream.of(COUNTs)
                .filter(count -> count != 0)
                .distinct()
                .forEach(count -> COMBs.put(
                        count, 
                        IntStream.rangeClosed(0, count)
                                .mapToObj(k   -> comb(count, k))
                                .toArray(size -> new BigInteger[size])));
        MEMO = IntStream.range(0, POWMODs.length)
                .mapToObj($ -> new BigInteger[M1])
                .toArray(size->new BigInteger[size][]);
    }
    static BigInteger paths(int i, int rest) {
        if (i == POWMODs.length)   return (rest == 0) ? ONE: ZERO;
        if (MEMO[i][rest] != null) return MEMO[i][rest];

        int        powMod = POWMODs[i];
        int        count  = COUNTs[powMod];
        BigInteger total  = ZERO; 
        for (int k = 0; k <= count; ++k) {
            long       nextRest = MOD.subtract(rest, MOD.multiply(powMod, k));
            BigInteger subPaths = paths(i + 1, (int) nextRest);
            BigInteger comb     = COMBs.get(count)[k];
            total               = total.add(comb.multiply(subPaths)).mod(M2);
        }
        return MEMO[i][rest] = total;
    }
    static BigInteger comb(int n, int k) {
        BigInteger[] coef = { ONE };
        PRIME_BITs.stream().forEach(prime -> {
            for (int pow = prime; pow <= n; pow *= prime) {
                int repeat = n / pow - k / pow - (n - k) / pow;
                for (int i = 0; i < repeat; ++i)
                    coef[0] = coef[0].multiply(valueOf(prime)).mod(M2); 
            }
        }); 
        return coef[0];
    }
}