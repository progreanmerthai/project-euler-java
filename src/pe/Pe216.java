package pe;

import java.math.BigInteger;
import java.util.stream.LongStream;
import static pe.util.Utils.run;

/**
 * <p>Perfect right-angled triangles</p>
 * <a href="https://projecteuler.net/problem=218">Problem 218</a>
 * This implementation takes too much time (about 5 minutes). 
 */
public class Pe216 {
    public static void main(String[] args) {
        run(() -> LongStream.rangeClosed(2, 50_000_000)
                .filter(n -> BigInteger.valueOf(2 * n * n - 1).isProbablePrime(5))
                .count());
    }
}
