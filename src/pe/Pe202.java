package pe;

import pe.util.Utils;

/**
 * <p>Laserbeam</p>
 * <a href="https://projecteuler.net/problem=202">Problem 202</a>
 */
public class Pe202 {
    /* 最終的にHaskell のワンライナーで書いた
     * 
     * Prelude Data.Numbers.Primes Data.List> let f bounces = (((a-1)`div`3) -) $ sum $ map (\(b, n) -> b*(1 + (a - (mod b 3)*n)`div`(3*n))) $ map (\ps->(-(-1)^(length ps), product ps)) $ drop 1 $ subsequences $ nub $ primeFactors a where a=(bounces+3)`div`2
     * Prelude Data.Numbers.Primes Data.List> f 1000001
     * 80840
     * Prelude Data.Numbers.Primes Data.List> f 12017639147
     * 1209002624
     */
    private static final long L0 = 12_017_639_147L;
    private static final long L1 = (L0 + 3) / 2L;
    private static final long L  = L1 / 3L;

    public static void main(String[] args) {
        Utils.run(Pe202::solve);
    }
    public static long solve() {
        long count = 0;
        for (int i = 0; i < L; ++i) {
            long x = i * 3L + 2;
            if (x % 5 != 0 
             && x % 11 != 0 
             && x % 17 != 0 
             && x % 23 != 0 
             && x % 29 != 0 
             && x % 41 != 0 
             && x % 47 != 0) count ++;
        }
        return count;
    }
}
