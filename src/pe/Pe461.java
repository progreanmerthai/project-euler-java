package pe;

import static pe.util.Utils.run;

import java.util.Arrays;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <p>Almost Pi</p>
 * <a href="https://projecteuler.net/problem=461">Problem 461</a>
 * ※ rewitten in Scala
 */
public class Pe461 {
    static final int N = 10000;
    static final int L = (int)(Math.log(Math.PI + 1) * N);
    static final ToIntFunction<int[]> SQSUM = arr -> IntStream.of(arr).map(n->n*n).sum();
    
    public static void main(String[] args) {
        run(Pe461::solve);
    }
    public static long solve() {
        double[] fs = initFs();
        double[] sums = initSums(fs);

        int[] sumPair   = fromBothEnds(sums, Math.PI);
        int[] lowerPair = fromBothEnds(fs, sums[sumPair[0]]);
        int[] upperPair = fromBothEnds(fs, sums[sumPair[1]]);

        return Stream.of(lowerPair, upperPair).mapToInt(SQSUM).sum();
    }
    private static double[] initFs() {
        double[] result = new double[L + 1];
        for (int i = 1; i <= L; ++i) result[i] = Math.exp(i/(double)N) - 1; 
        return result;
    }
    private static double[] initSums(double[] fs) {
        double[] result = new double[L*(L-1)/2];
        for (int i = L, k = 0; i >= 2; --i)
                for (int j = i - 1; j >= 1; --j)
                        result[k++] = fs[i] + fs[j];
        Arrays.sort(result);
        return result;
    }
    private static int[] fromBothEnds(double[] arr, double criteria) {
        double diff = Double.MAX_VALUE;
        int u = lowerBound(arr, criteria);
        int l = 0;
        int[] result = new int[2];
        while (l < u) {
            double diffTmp = criteria - (arr[l] + arr[u]);
            if (Math.abs(diffTmp) < diff) {
                diff = Math.abs(diffTmp);
                result[0] = l;
                result[1] = u;
            }
            if      (diffTmp < 0) u --;
            else if (diffTmp > 0) l ++;
            else break;
        }
        return result;
    }
    public static final int lowerBound(final double[] arr, final double value) {
        int low = 0;
        int high = arr.length;
        while (low < high) {
            int mid = (low + high) / 2;
            if (arr[mid] <= value) low = mid + 1;
            else high = mid;
        }
        return low - 1;
    }
}