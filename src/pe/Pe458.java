package pe;

import static pe.util.Utils.run;

/**
 * <p>Permutations of Project</p>
 * <a href="https://projecteuler.net/problem=458">Problem 458</a>
 */
public class Pe458 {
    static final long L = 1_000_000_000_000L;
    static final long M = 1_000_000_000;
    static final long[][] X = {
        {1,6,0,0,0,0},
        {1,1,5,0,0,0},
        {1,1,1,4,0,0},
        {1,1,1,1,3,0},
        {1,1,1,1,1,2},
        {1,1,1,1,1,1}};
    
    public static void main(String[] args) {
        run(() -> pow(X, L)[0][0] * 7 % M);
    }
    static long[][] pow(long[][] mtx, long e) {
        if (e == 1) return mtx;

        long[][] tmp = pow(mtx, e / 2);
        tmp = multiply(tmp, tmp);

        return ((e & 1) == 0) ? tmp : multiply(tmp, mtx);
    }
    static long[][] multiply(long[][] x1, long[][] x2) {
        long[][] result = new long[6][6];
        for (int row = 0; row < 6; ++row) {
            for (int col = 0; col < 6; ++col) {
                result[col][row] = ( x1[0][row] * x2[col][0]
                                   + x1[1][row] * x2[col][1]
                                   + x1[2][row] * x2[col][2]
                                   + x1[3][row] * x2[col][3]
                                   + x1[4][row] * x2[col][4]
                                   + x1[5][row] * x2[col][5]) % M;
            }
        }
        return result;
    }
}