package pe;

import static pe.util.Utils.run;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static pe.util.DoubleUtils.round;
import static pe.util.BigDecimalUtils.lt;
import static pe.util.BigDecimalUtils.multiply;
import static pe.util.BigDecimalUtils.times;
import static pe.util.BigDecimalUtils.toBigDecimals;

/**
 * <p>Scoring probabilities</p>
 * <a href="https://projecteuler.net/problem=286">Problem 286</a>
 */
public class Pe286 {
    static final MathContext MC = new MathContext(12, RoundingMode.HALF_UP);
    static final BigInteger[] ONE = new BigInteger[] { BigInteger.ONE };
    static final Map<Integer, BigInteger[]> MEMO = new HashMap<>();

    public static void main(String[] args) {
        run(Pe286::solve);
    }
    private static double solve() {
        BigInteger[] coefficients = f(50, 20);
        return round(search(toBigDecimals(coefficients), 50, 1), 10);
    }
    // coefficients of numerator of (p20*q^20 + ...+ p1q + p0)/q^50
    private static BigInteger[] f(int rest, int select) {
        if (hasMemo(rest, select)) return memo(rest, select);
        if (rest == 0) return ONE;
        
        BigInteger[] res = (select == 0)   ? mult(f(rest-1, select),   rest)      :
                           (rest > select) ? plus(prod(f(rest-1, select-1), rest), 
                                                  mult(f(rest-1, select),   rest)):
                                             prod(f(rest-1, select-1), rest);

        return memoise(rest, select, res);
    }
    static BigInteger[] memo(int rest, int select) {
        return MEMO.get(rest << 16 | select);
    }
    static boolean hasMemo(int rest, int select) {
        return MEMO.containsKey(rest << 16 | select);
    }
    static BigInteger[] memoise(int rest, int select, BigInteger[] result) {
        MEMO .put(rest << 16 | select, result);
        return result;
    }
    // (pn*q^n + ...+ p1*q + p0)*r
    static BigInteger[] mult(BigInteger[] ps, int r) {
        BigInteger[] res = new BigInteger[ps.length];
        for (int i = 0; i < ps.length; ++i) res[i] = times(ps[i], r);
        return res;
    }
    // (pn*q^n + ...+ p1*q + p0)*(q-r)
    static BigInteger[] prod(BigInteger[] ps, int r) {
        BigInteger[] res = new BigInteger[ps.length + 1];
        System.arraycopy(ps, 0, res, 1, ps.length);
        res[0] = BigInteger.ZERO;
        for (int i = 0; i < ps.length; ++i) res[i] = res[i].add(times(ps[i], -r));

        return res;
    }
    // (pn*q^n + ...+ p1*q + p0)+(rn*q^n + ...+ r1*q + r0)
    static BigInteger[] plus(BigInteger[] ps, BigInteger[] rs) {
        BigInteger[] res = new BigInteger[rs.length];
        for (int i = 0; i < ps.length; ++i) res[i] = ps[i].add(rs[i]);
        return res;
    }
    // 50,51,,,52.0,52.1,,,52.61,52.62,,,
    static double search(BigDecimal[] ps, double base, double inc) {
        if (inc < 0.000_000_000_01) return base;
        for (int i = 0; i < 10; i++) {
            if (lt(calc(base + inc * i, ps), 0.02)) {
                return search(ps, base + inc * (i - 1), inc / 10);
            }
        }
        return search(ps, base + inc * 9, inc / 10);
    }
    // probability for 20 points is (p20*q^20 +,,,+ p1*q + p0)/q^50
    static BigDecimal calc(double qd, BigDecimal[] ps) {
        BigDecimal q      = BigDecimal.valueOf(qd);
        BigDecimal powerQ = BigDecimal.ONE;
        BigDecimal num    = BigDecimal.ZERO;
        for (int i = 0; i <= 20; ++i) {
            num    = num.add(ps[i].multiply(powerQ));
            powerQ = powerQ.multiply(q);
        }
        return multiply(num, 1).divide(q.pow(50), MC);
    }
}