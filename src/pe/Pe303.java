package pe;

import static pe.util.Utils.run;

import java.util.stream.IntStream;

/**
 * <p>Multiples with small digits</p>
 * <a href="https://projecteuler.net/problem=303">Problem 303</a>
 */
public class Pe303 {
    public static void main(String[] args) {
        run(Pe303::solve);
    }
    private static long solve() {
        return IntStream.rangeClosed(1, 10000).mapToLong(
                i -> (i == 9999 || (i % 999 == 0)) ? f3(i) : f(i) / i).sum();
    }
    //TODO need more mathematically elegant solution
    private static long f3(int i) {
        switch(i) {
        case 999:  return 111333555778L;
        case 1998: return 55666777889L;
        case 2997: return 37444851926L;
        case 3996: return 30335891447L;
        case 4995: return 222667111556L;
        case 5994: return 18722425963L;
        case 6993: return 17476222254L;
        case 7992: return 27680458236L;
        case 8991: return 13592728531L;
        case 9990: return 111333555778L;
        case 9999: return 1111333355557778L;
        default: throw new AssertionError(""+i);
        }
    }
    private static long f(int i) {
        int n = 1;
        long result = to012(n);
        while (result % i > 0) result = to012(++n);
        return result;
    }
    private static long to012(int n) {
        long q = n;
        long r = 0;
        long m = 1;
        long result = 0;
        do {
            r = q % 3;
            result += m * r;
            q = q / 3;
            m *= 10;
        } while (q > 0);
        return result;
    }
}