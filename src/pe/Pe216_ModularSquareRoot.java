package pe;

import java.util.BitSet;
import static pe.util.Utils.run;
import static pe.util.Utils.primes;

/**
 * <p>Investigating the primality of numbers of the form 2n^2-1</p>
 * <a href="https://projecteuler.net/problem=216">Problem 216</a><br/>
 * (using Tonelli–Shanks algorithm)
 */
public class Pe216_ModularSquareRoot {
    static final int    L    = 50_000_000;
    static final int    PL   = (int)Math.sqrt(2L*L*L-1);

    static final BitSet BITS = new BitSet();
    static { BITS.flip(2, L + 1); }

    public static void main(String[] args) {
        run(Pe216_ModularSquareRoot::solve);
    }
    static long solve() {
        primes(PL).filter(p -> p % 8 == 1 || p % 8 == 7)
            .forEach(p-> {
                long np = smallestSquareRoot(p);
                for (long l = 0; l - np <= PL; l += p) {
                    mark(l - np, np);
                    mark(l + np, np);
                }
            });
        return BITS.stream().count();
    }
    static long smallestSquareRoot(long p) {
        long n = new Mod(p).squareRoot((p+1)/2);
        return (n > p/2) ? p - n : n;
    }
    static void mark(long m, long np) {
        if (m > 0 && m <= PL && m != np) BITS.set((int)m, false);
    }
    static class Mod {
        final long p;
        Mod(long p) { this.p = p; }

        long squareRoot(long n) {
            // Tonelli–Shanks algorithm
            int  s = 0;
            long q = p - 1;
            while (q % 2 == 0) {
                q /= 2;
                ++s;
            }
            long z = nr();
            long c = pow(z, q);
            long r = pow(n, (q + 1) / 2);
            long t = pow(n, q);
            long m = s;
            while (t % p != 1) {
                int  i = i(t, m);
                long b = pow(c, 1 << (m - i - 1));
                r = prod(r, b);
                t = prod(t, b, b);
                c = prod(b, b);
                m = i;
            }
            return r;
        }
        //TODO: 重複？ 
        long prod(long ... ms) {
            long result = 1;
            for (long m: ms) result = (result * m) % p;
            return result;
        }
        long pow(final long a, final long exp) {
            return (exp == 0) ? 1L:
                ((exp & 1L) == 1L ? a: 1L) * pow((a * a % p), exp >> 1L) % p;
        }
        int i(long t, long m) {
            for (int i = 1; i < m; ++i) if (pow(t, 1 << i) == 1) return i;
            throw new AssertionError();
        }
        long nr() {
            for (int n = 2; n < p; ++n)if (pow(n, (p - 1) / 2) == p - 1) return n;
            throw new AssertionError();
        }
    }    
}
