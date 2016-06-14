package pe;

import static pe.util.Utils.run;
import java.math.BigInteger;

/**
 * <p>Primonacci</p>
 * <a href="https://projecteuler.net/problem=304">Problem 304</a>
 */
public class Pe304 {
    static final long M = 1_234_567_891_011L;
    static final long L = 100_000;
    static final int  E = 14;

    static class PrevFs {
        long f2;
        long f1;
        PrevFs(long f2, long f1) {
            this.f2 = f2; 
            this.f1 = f1;
        }
        void step() {
            long f = (f1 + f2) % M;
            f2 = f1;
            f1 = f;
        }
        long proceed(long steps) {
            for (int i = 0; i < steps; ++i) step();
            return f1;
        }
    }
    public static void main(String[] args) {
        run(()->solve());
    }
    private static long solve() {
        PrevFs     fs  = new PrevFs(0, 1);
        BigInteger cur = BigInteger.TEN.pow(E).nextProbablePrime();

        long sum = fs.proceed(cur.longValue() % period() - 1);
        for (int n = 2; n <= L; ++n) {
            BigInteger next = cur.nextProbablePrime();
            sum = (sum + fs.proceed(next.subtract(cur).longValue())) % M;
            cur = next; 
        }        
        return sum;
    }
    private static long period() throws AssertionError {
        long i = 3;
        for (PrevFs fs = new PrevFs(1, 2); fs.f2 != 1 || fs.f1 != 1; ++i) fs.step();
        return i - 2;
    }
}