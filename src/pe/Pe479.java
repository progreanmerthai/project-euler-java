package pe;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;
import static pe.util.Utils.run;

import java.math.BigInteger;

/**
 * <p>Roots on the Rise</p>
 * <a href="https://projecteuler.net/problem=479">Problem 479</a>
 */
public class Pe479 {
    static BigInteger L = valueOf(1_000_000);
    static BigInteger M = valueOf(1_000_000_007);

    public static void main(String[] args) {
        run(Pe479::solve);
    }
    static long solve() {
        long result = 0;
        for (long k = 2; k <= L.longValue(); ++k) 
                result = (result + modGeometricSeries(1 - k * k)) % M.longValue();
        return result;
    }
    static long modGeometricSeries(long r) {
        BigInteger a   = valueOf(r);
        BigInteger rn  = valueOf(r).modPow(L, M);
        BigInteger inv = valueOf(1 - r).modInverse(M);
        
        return a.multiply(ONE.subtract(rn)).multiply(inv).mod(M).longValue();
    }
}