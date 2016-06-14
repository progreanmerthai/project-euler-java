package pe;

import static pe.util.Utils.run;

/**
 * <p>Swapping Counters</p>
 * <a href="https://projecteuler.net/problem=317">Problem 317</a>
 */
public class Pe321 {
    public static void main(String[] args) {
        run(Pe321::solve);
    }
    private static long solve() {
        long[] as = {0, 1, 10};
        long[] bs = {0, 3, 22};
        long sum = as[1] + as[2] + bs[1] + bs[2];
        for (int i = 3; i <= 20; ++i) sum += next(as) + next(bs);
        return sum;
    }
    static long next(long[] ns) {
        long cur_m = 7 * (ns[2] - ns[1]) + ns[0];
        ns[0] = ns[1];
        ns[1] = ns[2];
        ns[2] = cur_m;
        return cur_m;
    }
}