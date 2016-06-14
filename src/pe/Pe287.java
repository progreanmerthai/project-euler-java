package pe;

import pe.util.Utils;
import static pe.util.Utils.run;

/**
 * <p>Quadtree encoding (a simple compression algorithm)</p>
 * <a href="https://projecteuler.net/problem=287">Problem 287</a>
 */
public class Pe287 {
    static class Counter {
        @FunctionalInterface static interface OneColor {
            boolean eval(long x, long y, long size);
        }
        final OneColor onlyBlack;
        final OneColor onlyWhite;
        
        Counter(OneColor b, OneColor w) {
            this.onlyBlack= b;
            this.onlyWhite= w;
        }
        long count(long x, long y, long w) {
            return onlyBlack.eval(x, y, w) ? 0:
                   onlyWhite.eval(x, y, w) ? 1:
                   w == 1                  ? (dist2(x, y) <= R2 ? 0 : 1):
                                             split(x, y, w >> 1);
        }
        long split(long x, long y, long w) {
            long cLT = count(x,     y + w, w); 
            long cRT = count(x + w, y + w, w);
            long cLB = count(x,     y,     w); 
            long cRB = count(x + w, y,     w);
            
            return (cLT == cRT && cLB == cRT && cLB == cRB) 
                    ? cRB
                    : 1 + g(cLT) + g(cRT) + g(cLB) + g(cRB);
        }
        static long g(long c) { return (c < 2) ? 2 : c; }
    }
    static final int  N    = 24;
    static final long R    = Utils.pow(2, N) / 2;
    static final long R2   = R * R;
    static final long CX   = R;
    static final long CY   = R;

    static Counter lt = new Counter(
            (x, y, w) -> dist2(x,     y + w) < R2,
            (x, y, w) -> dist2(x + w, y)     > R2);
    static Counter rt = new Counter(
            (x, y, w) -> dist2(x + w, y + w) < R2,
            (x, y, w) -> dist2(x,     y)     > R2);
    static Counter lb = new Counter(
            (x, y, w) -> dist2(x,     y)     < R2,
            (x, y, w) -> dist2(x + w, y + w) > R2);

    static long dist2(long x, long y) {
        long x2 = x - CX;
        long y2 = y - CY;
        return x2 * x2 + y2 * y2; 
    }
    public static void main(String[] args) {
        run(()->lt.count(0, R, R) * 2 + rt.count(R, R, R) + lb.count(0, 0, R) + 1);
    }
}