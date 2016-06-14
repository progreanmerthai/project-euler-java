package pe;

import static java.math.BigInteger.valueOf;
import static pe.util.Utils.run;
import java.math.BigInteger;

/**
 * <p>Chinese leftovers</p>
 * <a href="https://projecteuler.net/problem=531">Problem 531</a>
 */
public class Pe531 {
    static final int FROM  = 1_000_000;
    static final int TO    = 1_005_000;
    static final int[] PHI = new int[(int)TO];
    public static void main(String[] args) {
        run(() -> {
            preparePhi();
            long sum = 0;
            for (int n = FROM ; n < TO; ++n)
                    for (int m = n + 1; m < TO; ++m) sum += f(n, m);
            return sum;
        });
    }
    static void preparePhi() {
        final int root = (int) Math.sqrt(TO);
        final int[] ds = new int[(int)TO];
        PHI[1] = 1;
        for (int i = 2; i < TO; ++i) {
            if (i <= root && PHI[i] == 0) {
                for (int j = i; j < TO; j += i) {
                    if (PHI[j] == 0) {
                        PHI[j] = i - 1;
                        ds[j] = j / i;
                    } else {
                        PHI[j] *= i - 1;
                        ds[j] /= i;
                    }
                    while (ds[j] % i == 0) {
                        PHI[j] *= i;
                        ds[j] /= i;
                    }
                }
            } else if (i > root) {
                PHI[i] = PHI[i] == 0 ? i - 1:
                    ds[i] == 1 ? PHI[i] :
                    PHI[i] * (ds[i] - 1);
            }
        }
    }
    static long f(int n, int m) {
        return g(PHI[n], n, PHI[m], m);
    }
    static long g(int a, int n, int b, int m) {
        long cd = gcd(n, m);
        if ((a - b) % cd != 0) return 0;

        long gcd = gcd(gcd(a, n), gcd(b, m));
        return g2(a / gcd, n / gcd, b / gcd, m / gcd) * gcd;
    }
    static long g2(long a, long n, long b, long m) {
        long gcd  = gcd(n, m);
        long diff = (m - n) / gcd;
        long lcm  = n * m   / gcd;

        long c = (a * m - b * n) / gcd % lcm;
        if (c < 0) c += lcm;
        
        long       gcd2   = gcd(diff, lcm);
        BigInteger m2     = valueOf(lcm / gcd2);
        long       result = valueOf(c   / gcd2)
                           .multiply(valueOf(diff / gcd2).modInverse(m2))
                           .mod(m2).longValue();
        while (result % n != a) result += m2.longValue();

        return result;
    }
    static long gcd(long d, long n) {
        return (n == 0) ? d : gcd(n, d % n);
    }
}