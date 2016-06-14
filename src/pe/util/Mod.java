package pe.util;

import java.math.BigInteger;

public class Mod {
    final long modulus;
    final BigInteger bi;
    public Mod(long modulus) {
        this.modulus = modulus;
        this.bi = BigInteger.valueOf(modulus);
    }
    public BigInteger asBinInteger() { return bi; }
    public long multiply(long a, long b) {
        return (a % this.modulus) * (b % this.modulus) % this.modulus;
    }
    public long product(long ... ms) {
        long result = 1;
        for (long m: ms) result = multiply(result, m % this.modulus); 
        return result;
    }
    public long sum(long ... ms) {
        long result = 0;
        for (long m: ms) result = add(result, m % this.modulus); 
        return result;
    }
    public long subtract(long a, long b) {
        long result = add(a, -b);
        if (result < 0) result += ((-result / modulus) + 1) * modulus;
        return result;
    }
    public long add(long a, long b) {
        return ((a % this.modulus) + (b % this.modulus)) % this.modulus;
    }
    public long pow(long r, int e) {
        if (e == 0) return 1;
        else if ((e & 1) == 1) return multiply(r, pow(multiply(r, r), e >> 1));
        else return pow(multiply(r, r), e >> 1);
    }
}
