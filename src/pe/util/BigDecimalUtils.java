package pe.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import static java.math.BigDecimal.valueOf;

public class BigDecimalUtils {
    public static boolean lt(BigDecimal d1, double d2) {
        return d1.compareTo(valueOf(d2)) < 0;
    }
    public static BigDecimal multiply(BigDecimal n, int m) {
        return n.multiply(BigDecimal.valueOf(m));
    }
    public static BigInteger times(BigInteger n, int m) {
        return n.multiply(BigInteger.valueOf(m));
    }
    public static BigDecimal[] toBigDecimals(BigInteger[] bigInts) {
        BigDecimal[] result = new BigDecimal[bigInts.length];
        for (int i = 0; i < bigInts.length; ++i) 
            result[i] = new BigDecimal(bigInts[i]);
        return result;
    }
}
