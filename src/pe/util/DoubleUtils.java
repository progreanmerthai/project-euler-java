package pe.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleUtils {
    public static double round(double d, int scale) {
        return BigDecimal.valueOf(d).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

}
