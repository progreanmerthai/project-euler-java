package pe;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import pe.util.Utils;

/**
 * <p>Criss Cross</p>
 * <a href="https://projecteuler.net/problem=166">Problem 166</a>
 */
public class Pe166 {
    static class Position {
        final int row, col;
        Position next;
    
        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
        int index() { return row * 4 + col; }
        Position and(int row, int col) { return this.next = new Position(row, col); }
        Position next(Grid m) {
            for (Position p = this.next; p != null; p = p.next)
                    if (!m.rangeAt(p).fixed()) return p;
            throw new AssertionError();
        }
    }
    static class Range {
        int low, high;
        final Set<Line> lines = new HashSet<>();
        
        Range(int low, int high) {
            this.low  = low;
            this.high = high;
        }
        int low()  { return this.low; }
        int high() { return this.high; }
        boolean fixed() { return this.low == this.high; }
        Stream<Line> lines() { return this.lines.stream(); }
        IntStream digitsPossible() { return IntStream.rangeClosed(this.low, this.high); }
    
        Range copy() { return new Range(this.low, this.high); }
    
        Range fix(int number) {
            this.low  = number;
            this.high = number;
            return this;
        }
    }
    static class Line {
        final Range[] ranges;
        final String  name;
    
        Line(String name, Range r1, Range r2, Range r3, Range r4) {
            this.name = name;
            this.ranges = new Range[] { r1, r2, r3, r4 };
            ranges().forEach(r -> r.lines.add(this));
        }
        Stream<Range> ranges() { return Arrays.stream(this.ranges); }
        boolean isFixed() { return ranges().allMatch(Range::fixed); }
    
        int rangeSum(ToIntFunction<? super Range> f) { 
            return ranges().mapToInt(f).sum(); 
        }
    
        boolean validate(int sum) {
            boolean completed = true;
            int     lowSum    = 0;
            int     highSum   = 0;
            for (Range r: ranges) {
                lowSum    += r.low;
                highSum   += r.high;
                completed &= r.fixed();
            }
            return completed && lowSum == sum || lowSum <= sum && highSum >= sum;
        }
        
        boolean narrow(int sum, int max, Set<Line> lines) {
            int diffL = sum - rangeSum(Range::low);
            int diffH = sum - rangeSum(Range::high);
    
            for (Range r: ranges) {
                if (r.fixed()) continue;
    
                int marginL = diffL + r.low;
                int marginH = diffH + r.high;
    
                if (r.low < marginH) {
                    r.low = marginH;
                    addModified(r, lines);
                }
                if (marginL < r.high) {
                    r.high = marginL;
                    addModified(r, lines);
                }
                if (r.lines().anyMatch(l -> !l.validate(sum))) return false;
            }
            return true;
        }
        void addModified(Range r, Set<Line> lines) {
            r.lines().filter(l ->  l != this).forEach(l -> lines.add(l));
        }
    }
    static class Grid {
        private static Range r(int max) { return new Range(0, max); }
        private static Range f(int digit) { return new Range(digit, digit); }
        
        final Range[] ranges;
        final int     sum;
        final int     max;
        final Line[]  lines;
    
        public Grid(int max, int n1, int n2, int n3, int n4) {
            this(max, f(n1),  r(max), r(max), r(max),
                      r(max), f(n2),  r(max), r(max),
                      r(max), r(max), f(n3),  r(max),
                      r(max), r(max), r(max), f(n4));
            deduce(new LinkedHashSet<>(Arrays.asList(lines)));
        }
        public Grid(int max, Range... rs) {
            this.max    = max;
            this.ranges = rs;
            this.lines  = new Line[] {
                new Line("r1", rs[ 0], rs[ 1], rs[ 2], rs[ 3]),
                new Line("r2", rs[ 4], rs[ 5], rs[ 6], rs[ 7]),
                new Line("r3", rs[ 8], rs[ 9], rs[10], rs[11]),
                new Line("r4", rs[12], rs[13], rs[14], rs[15]),
        
                new Line("c1", rs[ 0], rs[ 4], rs[ 8], rs[12]),
                new Line("c2", rs[ 1], rs[ 5], rs[ 9], rs[13]),
                new Line("c3", rs[ 2], rs[ 6], rs[10], rs[14]),
                new Line("c4", rs[ 3], rs[ 7], rs[11], rs[15]),
        
                new Line("d1", rs[ 0], rs[ 5], rs[10], rs[15]),
                new Line("d2", rs[ 3], rs[ 6], rs[ 9], rs[12])
            };
            sum = lines[8].rangeSum(Range::low);
        }
        Stream<Range> ranges() { return Arrays.stream(ranges); }
        boolean deduce(Set<Line> lines) {
            while (!lines.isEmpty()) {
                Line line = lines.iterator().next();
                lines.remove(line);
    
                if (!line.narrow(sum, max, lines)) return false;
            }
            return true;
        }
        boolean assume(Position pos, int digit) {
            return deduce(new LinkedHashSet<>(rangeAt(pos).fix(digit).lines));
        }
        Range rangeAt(Position pos) { return this.ranges[pos.index()]; }
    
        boolean isFixed() {
            return Arrays.stream(lines).allMatch(Line::isFixed);
        }
        int proceed(Position pos) {
            return rangeAt(pos).digitsPossible()
                    .map(digit -> copy().count(pos, digit))
                    .sum();
        }
        int count(Position pos, int digit) {
            return  !assume(pos, digit) ? 0 :
                    isFixed()           ? 1 :
                    proceed(pos.next(this));
        }
        Grid copy() {
            return new Grid(this.max, ranges().map(Range::copy).toArray(Range[]::new));
        }
    }
    static final int      MAX       = 9;
    static final Position FIRST_POS = new Position(0, 3);
    static {
           FIRST_POS .and(1, 2).and(2, 1).and(3, 0)
           .and(0, 1).and(0, 2).and(1, 0).and(1, 3)
           .and(2, 0).and(2, 3).and(3, 1).and(3, 2); 
    }
    public static void main(String[] args) {
        Utils.run(Pe166::solve);
    }
    static int solve() {
        int count = 0;
        for (int n1 = 0; n1 <= MAX; n1++)
            for (int n2 = 0; n2 <= MAX; n2++)
                for (int n3 = 0; n3 <= MAX; n3++)
                    for (int n4 = 0; n4 <= MAX; n4++)
                        count += new Grid(MAX, n1, n2, n3, n4).proceed(FIRST_POS);
        return count;
    }
}