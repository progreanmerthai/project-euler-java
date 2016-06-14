package pe;

/**
 * <p>Integer partition equations</p>
 * <a href="https://projecteuler.net/problem=207">Problem 207</a>
 */
public class Pe207 {
    public static void main(String[] args) {
        /*
        $k = 4^t - 2^t = 2^t(2^t - 1)$

        $n = [t]$

        range n: $\frac{n}{2^n-1}$ ... $\frac{n}{2^{n+1}-2}$

        range 1: $\frac{1}{1}$ ... $\frac{1}{2}$
        range 2: $\frac{2}{3}$ ... $\frac{2}{6}$
        ...
        range 17: $\frac{17}{131071}$ ... $\frac{17}{262142}$ (this range includes $\frac{1}{12345} = \frac{17}{209865}$)

        m for which $P(m) = \frac{1}{12345}$ is $209866 \times 209865 = 44043528090$

        So, the smallest m for which $P(m) < \frac{1}{12345}$ is $(209866 + 1) \times 209866 = 44043947822$
         */
    }
}
