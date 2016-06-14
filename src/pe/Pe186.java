package pe;

import java.util.LinkedList;
import pe.util.Utils;

/**
 * <p>Connectedness of a network</p>
 * <a href="https://projecteuler.net/problem=186">Problem 186</a>
 */
public class Pe186 {
    static class Generator {
        int caller, called;
        int count = 0;
        LinkedList<Integer> queue = new LinkedList<>();
        int generate() {
            int s;
            if (queue.size() == 55) {
                int i24 = queue.get(31);
                int i55 = queue.removeFirst();
                s = (i24 + i55) % 1_000_000;
            } else {
                int k = queue.size() + 1;
                s = (int)((100003L - 200003L*k + 300007L*k*k*k) % 1000000L);
            } 
            queue.addLast(s);
            return s;
        }
        void next() {
            do {
                caller = generate();
                called = generate();
            } while (caller == called);
            count++;
        }
    }
    static class User {
        final int tel;
        int       count = 1;
        User      upper = null;

        User(int tel) { this.tel = tel; }
        public void set(User to) {
            this.upper = to;
            to.count += this.count;
        }
        public User root() {
            return this.upper == null ? this: this.upper.root();
        }
        static void merge(User a, User b) {
            if (a == b) return;

            if      (a.tel == PM)   b.set(a);
            else if (b.tel == PM)   a.set(b);
            else if (a.tel < b.tel) b.set(a);
            else                    a.set(b);
        }
    }
    public static final int PM = 524287;

    public static void main(String[] args) {
        Utils.run(Pe186::solve);
    }
    public static long solve() {
        User[] index = new User[1_000_000];
        for (int i = 0; i < index.length; ++i) index[i] = new User(i);

        Generator g = new Generator();
        while (index[PM].count < 990000) {
            g.next();
            User.merge(index[g.caller].root(), index[g.called].root());
        }
        return g.count;
    }
}
