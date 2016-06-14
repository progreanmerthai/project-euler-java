package pe;

import static pe.util.Utils.run;

/**
 * <p>Licence plates</p>
 * <a href="https://projecteuler.net/problem=371">Problem 371</a>
 */
public class Pe371 {
    static int N  = 1000;
    static double[] As = new double[N / 2 + 1];
    static double[] Bs = new double[N / 2 + 1];
    static {
        As[0] = 1.0 / N; 
        Bs[0] = 0.0;
        As[1] = (double)(N - 2) / (double)N; 
        Bs[1] = 1.0 / N;
    }
    public static void main(String[] args) {
        run(Pe371::solve);
    }
    static double solve() {
        double sum = 0.0;
        double e   = 1.0;
        for (int i = 2; e > 1.0E-16; ++i) {
            e    = next();
            sum += i * e;
        };
        return Math.round(sum * 100_000_000) / 100_000_000.0;
    }
    private static double next() {
        double sum = 0.0;
        for (int j = N/2; j >= 1; --j) {
            sum   += (As[j] + Bs[j]) * j;
            As[j] = (As[j-1]*(N-2*j) + As[j]*(j+1)) / N;
            Bs[j] = (As[j-1] + Bs[j-1]*(N-2*(j-1))+Bs[j]*j) / N;
        }
        As[0] /= N;
        return sum / N;
    }
}
