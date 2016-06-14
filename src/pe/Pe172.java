package pe;

import static pe.util.Utils.run;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * <p>Investigating numbers with few repeated digits</p>
 * <a href="https://projecteuler.net/problem=172">Problem 172</a>
 */
public class Pe172 {
    static final int MAX_TIMES = 3;
    static final int COLUMNS   = 18;

    public static void main(String[] args) {
        run(() -> solve(COLUMNS));
    }
    static long solve(int cols) {
        return    numbers(10, cols)
                - numbers(9, cols - 1)
                - numbers(9, cols - 2) * alignments(1, cols - 1)
                - numbers(9, cols - 3) * alignments(2, cols - 2);
    }
    static long numbers(int digits, int cols) {
        return new Splitter(digits, MAX_TIMES).split(cols)
                .map(terms -> digitCombinations(digits, terms) * foldAlignments(terms))
                .mapToLong(n -> n)
                .sum();
    }
    static long foldAlignments(List<Integer> timess) {
        int positions = 1;
        long result   = 1;
        for (int times: timess) {
            result    *= alignments(times, positions);
            positions += times;
        }
        return result;
    }
    static long alignments(int times, int pos) {
        switch (times) {
        case 1: return pos;
        case 2: return pos * (pos + 1) / 2;
        case 3: return ((pos * (pos+1) * (2*pos+1)) / 6 + (pos * (pos+1)) / 2) / 2;
        default: throw new AssertionError();
        }
    }
    static long digitCombinations(int digits, List<Integer> terms) {
        return count(terms).reduce(
                perm(digits, terms.size()), (acc, times) -> acc / fact(times));
    }
    static LongStream count(List<Integer> terms) {
        int[] timess = new int[MAX_TIMES + 1];
        for (int n: terms) ++timess[n];
        return Arrays.stream(timess).asLongStream();
    }
    static long perm(int from, int n) {
        return fact(from - n + 1, from);
    }
    static long fact(long from, long to) {
        return LongStream.rangeClosed(from, to).reduce(1, (a, b)->a * b);
    }
    static long fact(long n) {
        return fact(2, n);
    }
    static class Splitter {
        final int digits;
        final int maxDigit;
        final List<List<Integer>> result = new ArrayList<>();
        
        Splitter(int digits, int maxDigit) {
            this.digits = digits;
            this.maxDigit = maxDigit;
        }
        Stream<List<Integer>> split(int rest) {
            split(rest, maxDigit, new ArrayList<>());
            return result.stream();
        }
        void split(int rest, int cur, List<Integer> terms) {
            if (cur * (digits - terms.size()) < rest) return;
            
            split(rest, cur - 1, clone(terms));
            if (cur <= rest) { 
                terms.add(cur);
                if (cur == rest) result.add(terms);
                if (cur < rest) split(rest - cur, cur, clone(terms));
            }
        }
        private static <T> List<T> clone(List<T> list) {
            return new ArrayList<>(list);
        }
    }
}