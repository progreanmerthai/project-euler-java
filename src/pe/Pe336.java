package pe;

import static pe.util.Utils.run;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>Maximix Arrangements</p>
 * <a href="https://projecteuler.net/problem=336">Problem 336</a>
 */
public class Pe336 {
    static int CARRIAGE_NUM = 11;
    static long ARRANGEMENT_3 = 0x0123456798aL;

    public static void main(String[] args) {
        run(Pe336::solve);
    }
    static String solve() {
        List<Long> list = new ArrayList<>();
        maximix(list, ARRANGEMENT_3, CARRIAGE_NUM - 3);
        Collections.sort(list);
        return toAlpha(list.get(2010));
    }
    static void maximix(List<Long> set, long arrangement, int fixed) {
        if (fixed > 0) {
            long next = rotateAt(arrangement, CARRIAGE_NUM - (fixed - 1));
            for (int i = 2; i <= CARRIAGE_NUM - fixed; ++i)
                    maximix(set, rotateAt(next, i), fixed - 1);
        } else set.add(arrangement);
    }
    static long rotateAt(long arrangement, int pos) {
        long x = 0L;
        for (long i = 0, r = right(arrangement, pos); i < pos; ++i, r >>= 4) {
            x <<= 4;
            x |=  r & 0xF;
        }
        return left(arrangement, pos) | x;
    }
    static long left(long arrangement, int pos) {
        return arrangement & ((-1L << (pos << 2)));
    }
    static long right(long arrangement, int pos) {
        return arrangement & ((1L << (pos << 2)) - 1);
    }
    static String toAlpha(long l) {
        char[] s = new char[CARRIAGE_NUM];
        for (int i = 0; i < CARRIAGE_NUM; ++i) {
            s[10 - i] = (char)('A' + l % 16);
            l >>= 4;
        }
        return String.valueOf(s);
    }
}
