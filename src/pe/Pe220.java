package pe;

import static pe.util.Utils.run;

/**
 * <p>Heighway Dragon</p>
 * <a href="https://projecteuler.net/problem=220">Problem 220</a>
 */
public class Pe220 {
    static int N = 50 + 1;
    static int [][] SC = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    static class Move {
        static final Move[] As = new Move[N];
        static final Move[] Bs = new Move[N];
        static final Move   U  = new Move(0, 0, 0); 
        static {
            As[0] = U.R().F().R();
            Bs[0] = U.L().F().L();
            for (int i = 1; i < N; ++i) {
                As[i] = U.a(i - 1).R().b(i - 1).F().R();
                Bs[i] = U.L().F().a(i - 1).L().b(i - 1);
            }
        }
        final long x;
        final long y;
        final int  d;
        Move(long x, long y, int d) {
            this.y = y;
            this.x = x;
            this.d = d;
        }
        @Override public String toString() {
            return String.format("%d,%d", this.x, this.y);
        }
        Move turn(int r) { return new Move(this.x, this.y, (this.d + r) % 4); }
        Move plus(Move move) {
            Move m = move.rotate(this.d);
            return new Move(this.x + m.x, this.y + m.y, m.d);
        }
        private Move rotate(int r) {
            long x2 =   this.x * SC[r][1] + this.y * SC[r][0];
            long y2 = - this.x * SC[r][0] + this.y * SC[r][1];
            return new Move(x2, y2, (this.d + r) % 4);
        }
        public Move F() {
            int r = (-this.d + 5) % 4;
            return new Move(this.x + SC[r][1], this.y + SC[r][0], this.d);
        }
        Move R() { return turn(1); }
        Move L() { return turn(3); }
        public Move a(int level) { return plus(As[level]); }
        public Move b(int level) { return plus(Bs[level]); }
    }
    public static void main(String[] args) {
        run(new Solver().solve(1_000_000_000_000L)::toString);
    }
    static class Solver {
        Move current = new Move(0, 1, 0);

        Move solve(long rest) {
            subA(N - 1, rest - 1);
            return current;
        }
        long subA(int level, long rest) {
            long c = (1L << level) - 1;
            if (c <= rest) {
                current = current.a(level - 1);
                return c;
            }
            long step = subA(level - 1, rest);
            if (step == rest) return step;

            current = current.R();
            step += subB(level - 1, rest - step);
            if (step == rest) return step;

            current = current.F().R();
            return step + 1;
        }
        long subB(int level, long rest) {
            long c = (1L << level) - 1;
            if (c <= rest) {
                current = current.b(level - 1);
                return c;
            }
            long step = 1;
            current = current.L().F();
            if (step == rest) return 1;

            step += subA(level - 1, rest - 1);
            if (rest == step) return step;

            current = current.L();
            return step + subB(level - 1, rest - step);
        }
    }
}