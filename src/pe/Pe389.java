package pe;

import java.util.function.IntToDoubleFunction;
import java.util.stream.IntStream;

import static pe.util.Utils.run;
import static java.lang.Math.pow;
import static pe.util.DoubleUtils.round;

/**
 * <p>Platonic Dice</p>
 * <a href="https://projecteuler.net/problem=389">Problem 389</a>
 */
public class Pe389 {
    public static void main(String[] args) {
        run(Pe389::solve);
    }
    static double solve() {
        double[] ps = f(20, f(12, f(8, f(6, f(4, new double[]{1.0})))));
        return round(variance(ps), 4);
    }
    static double[] f(int faces, double[] ps) {
        double[] result = new double[ps.length * faces];
        double[] tmp = new double[]{1.0};
        for (int i = 0; i < ps.length; ++i) {
            tmp = g(faces, tmp);
            for (int j = 0; j < tmp.length; ++j) {
                result[i + j] += ps[i] * tmp[j];
            }
        }
        return result;
    }
    static double[] g(int faces, double[] ps) {
        double[] result = new double[ps.length + faces - 1];
        for (int i = 0; i < faces; ++i) {
            for (int j = 0; j < ps.length; ++j) {
                result[j + i] += ps[j] / (double)faces;
            }
        }
        return result;
    }
    static double variance(double[] ps) {
        return sumBy(ps, i -> pow(i - mean(ps), 2) * ps[i-1]);
    }
    static double mean(double[] ps) {
        return sumBy(ps, i -> ps[i-1] * i);
    }
    static double sumBy(double[] ps, IntToDoubleFunction f) {
        return IntStream.rangeClosed(1, ps.length).mapToDouble(f).sum();
    }
}
