package pe;

import java.util.BitSet;
import java.util.Map;
import java.util.OptionalInt;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.IntConsumer;

import pe.util.Primes;
import pe.util.Utils;
import static java.util.OptionalInt.empty;

/**
 * <p>Prime connection</p>
 * <a href="https://projecteuler.net/problem=425">Problem 425</a>
 */
public class Pe425 {
    static final BitSet                     PRIME_BITS = Primes.bitset(10_000_000);
    static final Set<Integer>               SUSPENDED  = new TreeSet<>();
    static final Map<Integer, Set<Integer>> WAITING    = new TreeMap<>();
    static final Map<Integer, Integer>      FIXED      = new TreeMap<>();
    static long sum = 0L;

    public static void main(String[] args) {
        Utils.run(Pe425::solve);
    }
    static long solve() {
        fix(2, 2);
        PRIME_BITS.stream().skip(1).forEach(p -> {
            OptionalInt minConnected = minConnectedPrime(p, p);
            if (minConnected.isPresent()) {
                fix(p, FIXED.get(minConnected.getAsInt()));
                if (WAITING.containsKey(p)) propagate(p);
            } else suspend(p);
        });
        return SUSPENDED.stream().mapToLong(p->p).sum() + sum;
    }
    static void propagate(Integer prime) {
        Queue<Integer> queue = new PriorityQueue<>();
        queue.add(prime);
        do {
            Integer p = queue.remove();
            if (!WAITING.containsKey(p)) continue;
            for (Integer waiter: new TreeSet<>(WAITING.get(p))) {
                fix(waiter, prime);
                unsuspend(waiter);
                queue.add(waiter);
            }
        } while (!queue.isEmpty());
    }
    static void suspend(int p) {
        SUSPENDED.add(p);
        forEachConnectedPrime(p, c -> {
            if (!WAITING.containsKey(c)) WAITING.put(c, new TreeSet<>());
            WAITING.get(c).add(p);
        });
    }
    private static void unsuspend(Integer waiter) {
        forEachConnectedPrime(waiter, c -> {
            Set<Integer> set = WAITING.get(c);
            set.remove(waiter);
            if (set.isEmpty()) WAITING.remove(c);
        });
        SUSPENDED.remove(waiter);
    }
    private static void fix(Integer waiter, Integer prime) {
        if (waiter.compareTo(prime) < 0) sum += waiter;
        FIXED.put(waiter, prime);
    }
    static void forEachConnectedPrime(int p, IntConsumer f) {
        int tens = pow10(digits(p));
        for (int i = 1; i <= 9; ++i) 
            if (isPrime(p)) f.accept(tens * i + p);

        for (int pos = 1; pos <= digits(p); ++pos) {
            int digits1 = digits(p);
            for (int i = 0; i <= 9; ++i) if (filter(pos, digits1, i)) {
                int candidate = changeDigitAt(p, pos, i);
                if (p != candidate && isPrime(candidate)) f.accept(candidate);
            }
        }
    }
    static OptionalInt minConnectedPrime(int p, int limit) {
        OptionalInt dropped = dropLeftDigit(p);
        if (dropped.isPresent()) return dropped;

        for (int pos = 1; pos <= digits(p); ++pos) {
            OptionalInt next = minPrimeConnectedAt(p, pos);
            if (next.isPresent()) return next;
        }
        return empty();
    }
    static OptionalInt minPrimeConnectedAt(int src, int pos) {
        int digits = digits(src);
        for (int i = 0; i <= 9; ++i) if (filter(pos, digits, i)) {
            int candidate = changeDigitAt(src, pos, i);
            if (candidate < src && FIXED.containsKey(candidate)) 
                return OptionalInt.of(candidate);
        }
        return empty();
    }
    private static boolean filter(int pos, int digits, int n) {
        return (pos != digits || n != 0) &&
                (digits == 1 || pos > 1 || (n == 1||n == 3||n == 7||n == 9));
    }
    static OptionalInt dropLeftDigit(int prime) {
        int digits = digits(prime);
        if (digits == 1 || digitAt(prime, digits - 1) == 0) return empty();

        int dropped = prime % pow10(digits-1);
        return FIXED.containsKey(dropped) ? OptionalInt.of(dropped) : empty();
    }
    private static boolean isPrime(int maybePrime) {
        return PRIME_BITS.get(maybePrime);
    }
    static int digits(int p) {
        int result = 1;
        while ((p /= 10) > 0) result++;
        return result;
    }
    static int digitAt(int n, int pos) {
        return n / pow10(pos - 1) % 10;
    }
    static int changeDigitAt(int n, int pos, int m) {
        int tens = pow10(pos - 1);
        return (n / (tens * 10) * 10 + m) * tens + n % tens;
    }
    static int pow10(int e) {
        int result = 1;
        for (int i = 0; i < e; ++i) result *= 10;
        return result;
    }
}
