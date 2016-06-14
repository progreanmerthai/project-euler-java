package pe;

import static org.junit.Assert.*;

import org.junit.Test;

import pe.Pe315.RootClock;

public class Pe315Test {
    @Test public void testMax() {
        RootClock max = new Pe315.MaxsClock();
        max.calc(137);
        assertEquals(30, max.transition());
        max.calc(137);
        assertEquals(60, max.transition());
    }
    @Test public void testSam() {
        Pe315.SamsClock sam = new Pe315.SamsClock();
        sam.calc(137);
        assertEquals(40, sam.transition());
        sam.calc(137);
        assertEquals(80, sam.transition());
    }
}
