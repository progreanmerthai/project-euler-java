package pe.number_theory;

public class DiophantineEquation {
    static long[] pell(long n) {
        long g0 = 0;
        long h0 = 1;
        long k0 = (int)Math.sqrt(n);
        long y_1 = 1;
        long y0 = 0;
        long x0 = g0*y0 + h0*y_1;
        return pell(n, k0, g0, h0, k0, x0, y_1, y0);
    }
    static long[] pell(long n, long k0, long gn, long hn, long kn, long xn, long yn_1, long yn) {
        long g = - gn + kn * hn;
        long h = (n - g*g)/hn;
        long k = (k0 + g)/h;
        long y = yn_1 + kn * yn;
        long x = g*y + h*yn;
        if (h == hn) {  
            long xz = (xn*x + yn*y*n) / hn; 
            long yz = (xn*y + yn*x) / hn;
            return new long[] {
                    xz*xz+yz*yz*n,
                    2*xz*yz
                };
        } else if (gn == g) {
            long xz = (xn*xn + yn*yn*n) / hn;
            long yz = 2 * xn * yn / hn;
            return new long[] {xz, yz};
        }
        return pell(n, k0, g, h, k, x, yn, y);
    }
}
