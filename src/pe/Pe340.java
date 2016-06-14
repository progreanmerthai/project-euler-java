package pe;

import static pe.util.Utils.run;
import pe.util.Mod;

/**
 * <p>Crazy Function</p>
 * <a href="https://projecteuler.net/problem=340">Problem 340</a>
 */
public class Pe340 {
    static final long A = 1801088541;//21^7
    static final long B = 558545864083284007L;//7^21
    static final long C = 35831808;//12^7
    static final Mod  M = new Mod(1000000000);//10^9
    
    public static void main(String[] args) {
        run(Pe340::solve);
    }
    private static long solve() {
        return M.add(sum(f(0), 1, B % A + 1),
                     sum(sum(f(B % A +1), 1, A), -M.product(3, A-C, A), B / A));
    }
    // (4a-3c)((b-n)/a + 1)-c+n (mod m)
    static long f(long n) {
        return M.sum(M.multiply(M.add(4*A, -3*C), (B-n) / A + 1) , -C, n);
    }
    // (2s+(n-1)d)/2 (mod m)
    static long sum(long s, long diff, long n) {
        return n % 2 == 0 ? M.multiply(n/2, M.sum(s, s, diff * (n-1))):
                            M.multiply(n,   s + diff * (n-1) / 2);
    }
}