package pe.number_theory;

import static org.junit.Assert.*;

import org.junit.Test;

public class DiophantineEquationTest {

    @Test public void test2() {
        long[] answer = DiophantineEquation.pell(2);
        assertEquals(3L, answer[0]);
        assertEquals(2L, answer[1]);
    }
    @Test public void test3() {
        long[] answer = DiophantineEquation.pell(3);
        assertEquals(2L, answer[0]);
        assertEquals(1L, answer[1]);
    }
    @Test public void test6() {
        long[] answer = DiophantineEquation.pell(6);
        assertEquals(5L, answer[0]);
        assertEquals(2L, answer[1]);
    }
    @Test public void test8() {
        long[] answer = DiophantineEquation.pell(8);
        assertEquals(3L, answer[0]);
        assertEquals(1L, answer[1]);
    }
    @Test public void test10() {
        long[] answer = DiophantineEquation.pell(10);
        assertEquals(19L, answer[0]);
        assertEquals(6L, answer[1]);
    }
    @Test public void test12() {
        long[] answer = DiophantineEquation.pell(12);
        assertEquals(7L, answer[0]);
        assertEquals(2L, answer[1]);
    }
    @Test public void test15() {
        long[] answer = DiophantineEquation.pell(15);
        assertEquals(4L, answer[0]);
        assertEquals(1L, answer[1]);
    }
    @Test public void test29() {
        long[] answer = DiophantineEquation.pell(29);
        assertEquals(9801L, answer[0]);
        assertEquals(1820L, answer[1]);
    }
    @Test public void test61() {
        long[] answer = DiophantineEquation.pell(61);
        assertEquals(1766319049L, answer[0]);
        assertEquals(226153980L, answer[1]);
    }
    @Test public void test94() {
        long[] answer = DiophantineEquation.pell(94);
        assertEquals(2143295L, answer[0]);
        assertEquals(221064L, answer[1]);
    }

}
