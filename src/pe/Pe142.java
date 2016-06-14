package pe;

import static pe.util.Utils.run;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import pe.pythagorean_triple.Generator;

/**
 * <p>Perfect Square Collection</p>
 * <a href="https://projecteuler.net/problem=142">Problem 142</a>
 */
public class Pe142 {
    public static void main(String[] args) {
        run(Pe142::solve);
    }
    static long solve() {
        final TreeSet<Long> sums = new TreeSet<>();
        final Map<Long, List<long[]>> allTriples =
                new Generator(1000000).generate(5, 4, 3);

        allTriples.values().stream().filter(v -> v.size() > 1).forEach(sameA -> {
            sameA.stream()
            .forEach(xyc -> {
                int twos = countTwo(xyc[1]);
                sameA.stream()
                .filter(e -> e[1] < xyc[1] && allTriples.containsKey(xyc[1] >> twos))
                .forEach(xzc -> {
                    if ((allTriples.get(xyc[1] >> twos)).stream()
                    .anyMatch(yzz -> twos % 2 == 0 && yzz[1] == xzc[1] >> twos 
                                  || twos % 2 == 1 && yzz[2] == xzc[1] >> twos)) {
                        sums.add(xyc[0] + xyc[1] + xzc[1]); 
                    }
                });
            });
        });
        return sums.first();
    }
    private static int countTwo(long n) {
        int count = 0;
        while (n % 2 == 0) {
            ++count;
            n /= 2;
        }
        return count;
    }
}